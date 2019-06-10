package com.puente.puenteapp.model.service.impl;

import com.puente.puenteapp.model.dto.DashboardStats;
import com.puente.puenteapp.model.dto.Series;
import com.puente.puenteapp.model.entity.User;
import com.puente.puenteapp.model.repository.CourseEnrollmentRepository;
import com.puente.puenteapp.model.repository.IncomeRepository;
import com.puente.puenteapp.model.repository.OutcomeRepository;
import com.puente.puenteapp.model.repository.UserRepository;
import com.puente.puenteapp.model.service.DashboardService;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DashboardServiceImpl implements DashboardService {
    
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private IncomeRepository incomeRepository;
    
    @Autowired
    private OutcomeRepository outcomeRepository;
    
    @Autowired
    private CourseEnrollmentRepository courseEnrollmentRepository;
        
    @Override
    public DashboardStats getUserStats(Date dateInitial, Date dateFinal) {
        DashboardStats stats = new DashboardStats();
        Series series = new Series();
        List<Series> seriesList = new ArrayList<>();
        List<Object[]> objectList;
        // all users and donors
        List<User> users = userRepository.findAll();
        List<User> donors = users.stream().filter(user -> user.getRoles().stream().filter(role -> "DON".equals(role.getCode())).count() > 0 ).collect(Collectors.toList());
        
        // count of teaches, national and international donors
        Integer teachers = users.stream().filter(user -> user.getRoles().stream().filter(role -> "DOC".equals(role.getCode())).count() > 0 ).collect(Collectors.toList()).size();
        Integer nationalDonors = donors.stream().filter(donor -> "Guatemala".equals(donor.getCountry())).collect(Collectors.toList()).size();
        Integer internationalDonors = donors.size() - nationalDonors;
        stats.setIntlDonors(internationalDonors);
        stats.setNatlDonors(nationalDonors);
        stats.setTotalDonors(donors.size());
        stats.setTotalTeachers(teachers);
        
        // total incomes and outcomes
        Double totalIncomes = incomeRepository.getSumAmount(dateInitial, dateFinal);
        Double totalOutcomes = outcomeRepository.getSumAmount(dateInitial, dateFinal);
        stats.setTotalIncomes(totalIncomes);
        stats.setTotalOutcomes(totalOutcomes);
        
        // Avg students and total assigned courses
        Double avgStudents = courseEnrollmentRepository.getAvgAssistance(dateInitial, dateFinal);
        Integer totalAsignedCourses = (int) courseEnrollmentRepository.getCount(dateInitial, dateFinal);
        stats.setTotalAsignedCourses(totalAsignedCourses);
        stats.setAvgStudents(avgStudents);
        
        // Graph series on Incomes vs Outcomes by Month
        objectList = incomeRepository.getSumAmountByMonth(dateInitial, dateFinal);
        series.setName("Donaciones");
        series.setData(getIncomeOutcomeSeries(objectList));
        seriesList.add(series);
        objectList = outcomeRepository.getSumAmountByMonth(dateInitial, dateFinal);
        series = new Series();
        series.setName("Gastos");
        series.setData(getIncomeOutcomeSeries(objectList));
        seriesList.add(series);
        stats.setIncomesVsOutcomesByMonth(seriesList);
        
        // Graph series on Outcomes by Category and Month
        seriesList = new ArrayList<>();
        objectList = outcomeRepository.getSumAmountByMonthAndOutcomeCategoryType(dateInitial, dateFinal);
        series = new Series();
        series.setName("Capital");
        series.setData(getOutcomeSumByTypeAndMonth(objectList, "CAPITAL"));
        seriesList.add(series);
        series = new Series();
        series.setName("Operativo");
        series.setData(getOutcomeSumByTypeAndMonth(objectList, "OPERATIVO"));
        seriesList.add(series);
        stats.setOutcomeByTypeAndMonth(seriesList);
        
        List<Object[]> countStudentsByGradeAndTime = courseEnrollmentRepository.getCountStudentsByGradeAndTime(dateInitial, dateFinal);
        List<Series> countStudentsByGradeAndTimeSeries = new ArrayList<>();
        
        do {
            countStudentsByGradeAndTime = fillCourseSeries(countStudentsByGradeAndTime, countStudentsByGradeAndTimeSeries);
        } while (!countStudentsByGradeAndTime.isEmpty());
        
        stats.setCoursesByGradeAndTime(countStudentsByGradeAndTimeSeries);
        
        return stats;
    }
    
    private List<Object[]> fillCourseSeries(List<Object[]> countStudentsByGradeAndTime, List<Series> countStudentsByGradeAndTimeSeries) {
        if (!countStudentsByGradeAndTime.isEmpty()) {
            List<Long> data = new ArrayList<>();
            List<Integer> toRemove = new ArrayList<>();
            long[] courseArray = new long[9];
            int index = 0;
            String course = (String) countStudentsByGradeAndTime.get(index)[1];
            for (int i = 0; i < courseArray.length; i++) {
                courseArray[i] = 0;
            }
            for (Object[] objects : countStudentsByGradeAndTime) {
                if (course.equals(objects[1])) {
                    courseArray[(int) objects[2] - 1] = ((long) objects[0]);
                    toRemove.add(index);
                }
                index++;
            }
            Collections.reverse(toRemove);
            for (int i : toRemove) {
                countStudentsByGradeAndTime.remove(i);
            }
            for (long i : courseArray) {
                data.add(i);
            }

            Series series = new Series();
            series.setName(course);
            series.setData(data);
            countStudentsByGradeAndTimeSeries.add(series);
        }
        
        return countStudentsByGradeAndTime;
        
    }
    
    
    private int getMaxNumber(List<Object[]> list) {
        int max = 0;
        for (int i = 0; i < list.size(); i++) {
            if ((int)list.get(i)[2] > max) {
                max = (int)list.get(i)[2];
            }
        }
        return max;
    }
    
    /**
     * gets the Double data for outcome by type and month
     * @param sumByMonthAndType
     * @param type
     * @return
     */
    private List<Double> getOutcomeSumByTypeAndMonth(List<Object[]> sumByMonthAndType, String type) {
        List<Double> data = new ArrayList<>();
        if (!sumByMonthAndType.isEmpty()) {
            double[] typeArray = new double[(int) getMaxNumber(sumByMonthAndType)];
            for (int i = 0; i < typeArray.length; i++) {
                typeArray[i] = 0;
            }
            for (Object[] objects : sumByMonthAndType) {
                if (type.equals(objects[1])) {
                    typeArray[(int) objects[2] - 1] = ((Double) objects[0]);
                }
            }
            for (double d : typeArray) {
                data.add(d);
            }
        }
        
        return data;
    }
    
    /**
     * gets the Double data for income/outcome by month
     * @param sumByMonth
     * @return
     */
    private List<Double> getIncomeOutcomeSeries(List<Object[]> sumByMonth) {
        List<Double> data = new ArrayList<>();
        if (!sumByMonth.isEmpty()) {
            double[] amountArray = new double[(int) sumByMonth.get(sumByMonth.size()-1)[1]];
            for (int i = 0; i < amountArray.length; i++) {
                amountArray[i] = 0;
            }
            
            for (Object[] objects : sumByMonth) {
                amountArray[(int) objects[1] - 1] = ((BigDecimal) objects[0]).doubleValue();
            }
            for (double d : amountArray) {
                data.add(d);
            }
        }
        
        return data;
    }
    
}
