package com.example.demo.controller;

import java.awt.print.Pageable;
import java.net.http.HttpHeaders;
import java.util.ArrayList;
import java.util.List;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.models.User;
import com.example.demo.repository.userRepository;

import org.springframework.data.domain.Sort;


@RequestMapping("/User")
@RestController
public class userController {

		
	@Autowired
	userRepository userrep; 
	
	
	
	@PostMapping("/adduser")
	public User create(@RequestBody User user) {
		return userrep.save(user);
	}
		
	
	@GetMapping("/users")
    public ResponseEntity<List<User>> getAllUser2(
            @RequestParam(defaultValue = "0") Integer pageNo,
            @RequestParam(defaultValue = "2") Integer pageSize,
            @RequestParam(defaultValue = "id") String sortBy)
    {

        PageRequest paging = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));
        Page<User> pagedResult = userrep.findAll(paging);

        List<User> list;

        if(pagedResult.hasContent()) {
            list= pagedResult.getContent();
        } else {
            list = new ArrayList<User>();
        }
        return new ResponseEntity<List<User>>(list, HttpStatus.OK);

    }
	      
	
	@DeleteMapping("/deleteuser/{id}")
	public void delete(@PathVariable long id) {
		userrep.deleteById(id);
		
	}
	
	
	@PutMapping("/updateuser/{id}")
	public User update(@PathVariable(value = "id") long Id,@RequestBody User user) {
	          
		User u = userrep.findById(Id);
		
		u.setUsername(user.getUsername());
		u.setEmail(user.getEmail());
		u.setPassword(user.getPassword());

		
	    u= userrep.save(u);
	    return u;
		
		
}
	}


