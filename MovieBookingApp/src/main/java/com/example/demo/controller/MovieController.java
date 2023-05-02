package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
import com.example.demo.service.MovieService;
import com.example.demo.service.TicketService;

@RestController
@RequestMapping("api/v1.0/moviebooking")
public class MovieController {

	@Autowired
	private MovieService movieService;
	
	@Autowired
	private TicketService ticketService;
	
	@PostMapping("/addMovie")
	public ResponseEntity<?> addMovie(@RequestBody Movie movie) throws MovieAlreadyPresentException{
		
		if(movieService.addMovie(movie)!= null) {
			return new ResponseEntity<Movie>(movie, HttpStatus.CREATED);
		}
		
		return new ResponseEntity<String>("Movie is null", HttpStatus.NO_CONTENT);
	}
	
	@GetMapping("/getAllMovies")
	public ResponseEntity<?> getAllMovies() throws NoMoviePresentException, MovieIdNotPresentException{
		
		List<Movie> movieList=movieService.getAllMovies();
		
		if(movieList!=null) {
			
			for(Movie m: movieList) {
				List<Ticket> ticketList=ticketService.getAllTickets(m.getMovieId());
				m.setTicketList(ticketList);
			}
			return new ResponseEntity<List<Movie>>(movieList, HttpStatus.OK);
		}
		
		return new ResponseEntity<String>("MovieList is empty", HttpStatus.NO_CONTENT);
		
	}
	
	@DeleteMapping("/deleteMovie/{mid}")
	public ResponseEntity<?> deleteMovie(@PathVariable int mid) throws MovieIdNotPresentException{
		
		if(ticketService.deleteTicket(mid) & movieService.deleteMovie(mid)) {
			return new ResponseEntity<String>(mid+" Movie & Tickets Deleted", HttpStatus.OK);
		}
		return new ResponseEntity<String>(mid+" Movie is not deleted", HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@PutMapping("/updateMovie")
	public ResponseEntity<?> updateMovie(@RequestBody Movie movie) throws MovieIdNotPresentException{
		
		if(movieService.updateMovie(movie)) {
			return new ResponseEntity<String>(movie.getMovieName()+" Movie Updated", HttpStatus.OK);
		}
		return new ResponseEntity<String>(movie.getMovieName()+" Movie is not updated", HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@GetMapping("/searchByMovieId/{mid}")
	public ResponseEntity<?> searchByMovieId(@PathVariable int mid) throws MovieIdNotPresentException{
		
		if(movieService.searchMovieById(mid)!= null) {
			List<Ticket> ticketList=ticketService.getAllTickets(mid);
			movieService.searchMovieById(mid).setTicketList(ticketList);
			return new ResponseEntity<Movie>(movieService.searchMovieById(mid), HttpStatus.OK);
		}
		
		return new ResponseEntity<String>("Movie not present", HttpStatus.NO_CONTENT);
	}
	
	
	
}
