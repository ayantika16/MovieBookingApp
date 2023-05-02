package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.exceptions.MovieIdNotPresentException;
import com.example.demo.model.Movie;
import com.example.demo.model.Ticket;
import com.example.demo.service.MovieService;
import com.example.demo.service.TicketService;

@RestController
@RequestMapping("/api/v1.0/bookTicket")
public class TicketController {
	
	@Autowired
	private TicketService ticketService;
	
	@Autowired
	private MovieService movieService;
	
	@PostMapping("/bookTicket/{mid}")
	public ResponseEntity<?> bookTicket(@PathVariable("mid") int movieId, @RequestBody Ticket ticket) throws MovieIdNotPresentException{
		
		Movie mov=movieService.searchMovieById(movieId);
		
		if(mov!= null) {
			int avl=mov.getSeatsAvailable();
			mov.setSeatsAvailable(avl-ticket.getBookedSeats());
			ticket.setMovie_id_fk(movieId);
			ticket.setBookedSeats(ticket.getBookedSeats());
			ticket.setSeatsAvailable(avl-ticket.getBookedSeats());
		
		
		if(movieService.updateMovie(mov) && ticketService.bookTicket(ticket)) {
			
			return new ResponseEntity<Ticket>(ticket, HttpStatus.CREATED);
		}
		}
		return new ResponseEntity<String>("Ticket Cannot be booked", HttpStatus.INTERNAL_SERVER_ERROR);
		
		
	}
}
