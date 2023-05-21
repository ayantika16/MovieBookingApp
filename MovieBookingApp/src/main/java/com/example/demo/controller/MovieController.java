package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
//import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.exceptions.MovieAlreadyPresentException;
import com.example.demo.exceptions.MovieIdNotPresentException;
import com.example.demo.exceptions.NoMoviePresentException;
import com.example.demo.model.Movie;
import com.example.demo.model.Ticket;
import com.example.demo.service.DataPublisherServiceImpl;
import com.example.demo.service.MovieService;
import com.example.demo.service.TicketService;

@RestController
@RequestMapping("api/v1/moviebooking")
@CrossOrigin(origins = "http://localhost:4200")
public class MovieController {

	@Autowired
	private MovieService movieService;
	
	@Autowired
	private TicketService ticketService;
	
	@Autowired
	DataPublisherServiceImpl dp;
	
	@PostMapping("/addMovie")
	//@PreAuthorize("hasRole('Admin')")
	public ResponseEntity<?> addMovie(@RequestBody Movie movie) throws MovieAlreadyPresentException{
		
		if(movieService.addMovie(movie)!= null) {
			
			dp.setTemp(movie.getMovieName()+" Added Successfully");
			return new ResponseEntity<Movie>(movie, HttpStatus.CREATED);
			
		}
		
		dp.setTemp(movie.getMovieName()+"not added");
		
		return new ResponseEntity<String>("Movie is null", HttpStatus.NO_CONTENT);
	}
	@CrossOrigin(origins = "http://localhost:4200")
	@GetMapping("/getAllMovies")
	//@PreAuthorize("hasRole(2)")
	public ResponseEntity<?> getAllMovies() throws NoMoviePresentException, MovieIdNotPresentException{
		
		List<Movie> movieList=movieService.getAllMovies();
		
		if(movieList!=null) {
			
			for(Movie m: movieList) {
				List<Ticket> ticketList=ticketService.getAllTickets(m.getMovieId());
				m.setTicketList(ticketList);
			}
			dp.setTemp("Fetched All Movies");
			return new ResponseEntity<List<Movie>>(movieList, HttpStatus.OK);
		}
		
		return new ResponseEntity<String>("MovieList is empty", HttpStatus.NO_CONTENT);
		
	}
	
	@DeleteMapping("/deleteMovie/{mid}")
	//("hasRole('Admin')")
	public ResponseEntity<?> deleteMovie(@PathVariable int mid) throws MovieIdNotPresentException{
		
		if(  ticketService.deleteTicket(mid) &&  movieService.deleteMovie(mid)) {
			dp.setTemp(mid+" Movie & Tickets deleted");
			return new ResponseEntity<String>(mid+" Movie & Tickets Deleted", HttpStatus.OK);
		}
		return new ResponseEntity<String>(mid+" Movie is not deleted", HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@PutMapping("/updateMovie")
	//@PreAuthorize("hasRole('Admin')")
	public ResponseEntity<?> updateMovie(@RequestBody Movie movie) throws MovieIdNotPresentException{
		
		if(movieService.updateMovie(movie)) {
			dp.setTemp(movie.getMovieName()+" Updated Successfully");
			return new ResponseEntity<String>(movie.getMovieName()+" Movie Updated", HttpStatus.OK);
		}
		return new ResponseEntity<String>(movie.getMovieName()+" Movie is not updated", HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@GetMapping("/searchByMovieId/{mid}")
	//("hasRole('User')")
	public ResponseEntity<?> searchByMovieId(@PathVariable int mid) throws MovieIdNotPresentException{
		
		if(movieService.searchMovieById(mid)!= null) {
			List<Ticket> ticketList=ticketService.getAllTickets(mid);
			movieService.searchMovieById(mid).setTicketList(ticketList);
			return new ResponseEntity<Movie>(movieService.searchMovieById(mid), HttpStatus.OK);
		}
		
		return new ResponseEntity<String>("Movie not present", HttpStatus.NO_CONTENT);
	}
	
	
	
}
