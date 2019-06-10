package com.puente.puenteapp.controller;

import static org.springframework.web.bind.annotation.RequestMethod.POST;
import static org.springframework.web.bind.annotation.RequestMethod.PUT;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.puente.puenteapp.util.PuenteException;
import com.puente.puenteapp.model.entity.Timeline;
import com.puente.puenteapp.model.repository.TimelineRepository;

@RestController
@RequestMapping("/api/timelines")
public class TimelineController extends BaseController {
	
	@Autowired
    private TimelineRepository repository;
	
	@GetMapping("/")
    public List<Timeline> getAll() {
        return repository.findAll(new Sort(Sort.Direction.ASC, "date"));
    }
	
	@GetMapping("/{id}")
    public Timeline get(@PathVariable(value = "id") Integer id) throws PuenteException {
        return getById(repository, id);
    }
	
	@RequestMapping(method = POST, value = "/new")
    public Timeline create(@RequestBody Timeline timeline) {
        return repository.save(timeline);
    }

    @RequestMapping(method = PUT, value = "/{id}")
    public ResponseEntity<Timeline> update(@PathVariable(value = "id") Integer id, @RequestBody Timeline dto) throws PuenteException {
    	Timeline timeline = getById(repository, id);
        if (timeline == null) {
            return ResponseEntity.notFound().build();
        }
        copy(dto, timeline);
        return ResponseEntity.ok(repository.save(timeline));
    }

}
