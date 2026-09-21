package in.co.rays.project_3.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import in.co.rays.project_3.dto.BaseDTO;
import in.co.rays.project_3.dto.MovieDTO;
import in.co.rays.project_3.exception.ApplicationException;
import in.co.rays.project_3.exception.DuplicateRecordException;
import in.co.rays.project_3.model.ModelFactory;
import in.co.rays.project_3.model.MovieModelInt;
import in.co.rays.project_3.util.DataUtility;
import in.co.rays.project_3.util.DataValidator;
import in.co.rays.project_3.util.PropertyReader;
import in.co.rays.project_3.util.ServletUtility;

/**
 * Movie functionality CRUD operation
 * 
 * @author Hemendra mewada
 *
 */
@WebServlet(urlPatterns = { "/ctl/MovieCtl" })
public class MovieCtl extends BaseCtl {

	private static Logger log = Logger.getLogger(MovieCtl.class);

	@Override
	protected boolean validate(HttpServletRequest request) {

		log.debug("MovieCtl Method validate Started");

		boolean pass = true;

		String movieName = request.getParameter("movieName");
		String director = request.getParameter("director");
		String producer = request.getParameter("producer");
		String duration = request.getParameter("duration");
		String genre = request.getParameter("genre");
		String language = request.getParameter("language");
		String releaseDate = request.getParameter("releaseDate");

		// Movie Name
		if (DataValidator.isNull(movieName)) {
			request.setAttribute("movieName",
					PropertyReader.getValue("error.require", "Movie Name"));
			pass = false;
		} else if (!DataValidator.isName(movieName)) {
			request.setAttribute("movieName",
					PropertyReader.getValue("error.name", "Movie Name"));
			pass = false;
		}

		// Director
		if (DataValidator.isNull(director)) {
			request.setAttribute("director",
					PropertyReader.getValue("error.require", "Director"));
			pass = false;
		} else if (!DataValidator.isName(director)) {
			request.setAttribute("director",
					PropertyReader.getValue("error.name", "Director"));
			pass = false;
		}

		// Producer
		if (DataValidator.isNull(producer)) {
			request.setAttribute("producer",
					PropertyReader.getValue("error.require", "Producer"));
			pass = false;
		} else if (!DataValidator.isName(producer)) {
			request.setAttribute("producer",
					PropertyReader.getValue("error.name", "Producer"));
			pass = false;
		}

		// Duration (must be number)
		if (DataValidator.isNull(duration)) {
			request.setAttribute("duration",
					PropertyReader.getValue("error.require", "Duration"));
			pass = false;
		} else if (!DataValidator.isInteger(duration)) {
			request.setAttribute("duration",
					"Duration must be numeric (in minutes)");
			pass = false;
		}

		// Genre
		if (DataValidator.isNull(genre)) {
			request.setAttribute("genre",
					PropertyReader.getValue("error.require", "Genre"));
			pass = false;
		} else if (!DataValidator.isName(genre)) {
			request.setAttribute("genre",
					PropertyReader.getValue("error.name", "Genre"));
			pass = false;
		}

		// Language
		if (DataValidator.isNull(language)) {
			request.setAttribute("language",
					PropertyReader.getValue("error.require", "Language"));
			pass = false;
		} else if (!DataValidator.isName(language)) {
			request.setAttribute("language",
					PropertyReader.getValue("error.name", "Language"));
			pass = false;
		}

		// Release Date
		if (DataValidator.isNull(releaseDate)) {
			request.setAttribute("releaseDate",
					PropertyReader.getValue("error.require", "Release Date"));
			pass = false;
		} else if (!DataValidator.isDate(releaseDate)) {
			request.setAttribute("releaseDate",
					PropertyReader.getValue("error.date", "Release Date"));
			pass = false;
		}

		log.debug("MovieCtl Method validate Ended");
		return pass;
	}

	@Override
	protected BaseDTO populateDTO(HttpServletRequest request) {

		log.debug("MovieCtl Method populateDTO Started");

		MovieDTO dto = new MovieDTO();

		dto.setId(DataUtility.getLong(request.getParameter("id")));
		dto.setMovieName(DataUtility.getString(request.getParameter("movieName")));
		dto.setDirector(DataUtility.getString(request.getParameter("director")));
		dto.setProducer(DataUtility.getString(request.getParameter("producer")));
		dto.setDuration(DataUtility.getString(request.getParameter("duration")));
		dto.setGenre(DataUtility.getString(request.getParameter("genre")));
		dto.setLanguage(DataUtility.getString(request.getParameter("language")));
		dto.setReleaseDate(DataUtility.getDate(request.getParameter("releaseDate")));

		populateBean(dto, request);

		log.debug("MovieCtl Method populateDTO Ended");
		return dto;
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		log.debug("MovieCtl Method doGet Started");

		long id = DataUtility.getLong(request.getParameter("id"));
		MovieModelInt model = ModelFactory.getInstance().getMovieModel();

		if (id > 0) {
			try {
				MovieDTO dto = model.findByPK(id);
				ServletUtility.setDto(dto, request);
			} catch (ApplicationException e) {
				log.error(e);
				ServletUtility.handleException(e, request, response);
				return;
			}
		}

		ServletUtility.forward(getView(), request, response);
		log.debug("MovieCtl Method doGet Ended");
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		log.debug("MovieCtl Method doPost Started");

		String op = DataUtility.getString(request.getParameter("operation"));
		MovieModelInt model = ModelFactory.getInstance().getMovieModel();
		long id = DataUtility.getLong(request.getParameter("id"));

		if (OP_SAVE.equalsIgnoreCase(op) || OP_UPDATE.equalsIgnoreCase(op)) {

			MovieDTO dto = (MovieDTO) populateDTO(request);

			try {
				if (id > 0) {
					model.update(dto);
					ServletUtility.setSuccessMessage("Movie updated successfully", request);
				} else {
					try {
						model.add(dto);
						ServletUtility.setSuccessMessage("Movie added successfully", request);
					} catch (DuplicateRecordException e) {
						ServletUtility.setDto(dto, request);
						ServletUtility.setErrorMessage("Movie already exists", request);
						return;
					}
				}

				ServletUtility.setDto(dto, request);

			} catch (ApplicationException | DuplicateRecordException e) {
				log.error(e);
				ServletUtility.handleException(e, request, response);
				return;
			}

		} else if (OP_DELETE.equalsIgnoreCase(op)) {

			MovieDTO dto = (MovieDTO) populateDTO(request);
			try {
				model.delete(dto);
				ServletUtility.redirect(ORSView.MOVIE_LIST_CTL, request, response);
				return;
			} catch (ApplicationException e) {
				log.error(e);
				ServletUtility.handleException(e, request, response);
				return;
			}

		} else if (OP_CANCEL.equalsIgnoreCase(op)) {

			ServletUtility.redirect(ORSView.MOVIE_LIST_CTL, request, response);
			return;

		} else if (OP_RESET.equalsIgnoreCase(op)) {

			ServletUtility.redirect(ORSView.MOVIE_CTL, request, response);
			return;
		}

		ServletUtility.forward(getView(), request, response);
		log.debug("MovieCtl Method doPost Ended");
	}

	@Override
	protected String getView() {
		return ORSView.MOVIE_VIEW;
	}
}