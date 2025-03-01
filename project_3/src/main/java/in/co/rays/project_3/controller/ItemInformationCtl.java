package in.co.rays.project_3.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import in.co.rays.project_3.dto.BaseDTO;
import in.co.rays.project_3.dto.ItemInformationDTO;
import in.co.rays.project_3.exception.ApplicationException;
import in.co.rays.project_3.exception.DuplicateRecordException;
import in.co.rays.project_3.model.ModelFactory;
import in.co.rays.project_3.model.ItemInformationModelInt;
import in.co.rays.project_3.util.DataUtility;
import in.co.rays.project_3.util.DataValidator;
import in.co.rays.project_3.util.PropertyReader;
import in.co.rays.project_3.util.ServletUtility;

@WebServlet(name = "ItemInformationCtl", urlPatterns = { "/ctl/ItemInformationCtl" })

public class ItemInformationCtl extends BaseCtl {

	@Override
	protected void preload(HttpServletRequest request) {

		Map<Integer, String> map = new HashMap();
		map.put(1, "High");
		map.put(2, "Low");

		request.setAttribute("productt", map);

		/*
		 * ap<Integer, String> map1 = new HashMap(); map1.put(1, "Cash"); map1.put(2,
		 * "online"); map1.put(3, "netbanking"); map1.put(4, "cheque");
		 * 
		 * request.setAttribute("transaction_typee", map1);
		 */

	}

	protected boolean validate(HttpServletRequest request) {
		boolean pass = true;

		if (DataValidator.isNull(request.getParameter("title"))) {
			request.setAttribute("title", PropertyReader.getValue("error.require", "title"));
			pass = false;

		} else if (!DataValidator.isName(request.getParameter("title"))) {
			request.setAttribute("title", "only letter are allowed ");
			pass = false;
		}

		else if (request.getParameter("title").length() <= 2 || request.getParameter("title").length() >= 15) {
			request.setAttribute("title", " title bteween 2 to 15");
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("overView"))) {
			request.setAttribute("overView", PropertyReader.getValue("error.require", "overView"));
			pass = false;
		}  else if (!DataValidator.validateAlphanumeric(request.getParameter("overView"))) {
			request.setAttribute("overView", "special charcter not allowed ");
			pass = false;
		}

		else if (request.getParameter("overView").length() <= 10 || request.getParameter("overView").length() >= 250) {
			request.setAttribute("overView", " overView bteween 10 to 250");
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("cost"))) {
			request.setAttribute("cost", PropertyReader.getValue("error.require", "cost"));
			pass = false;
		}

		else if (!DataValidator.isDouble(request.getParameter("cost"))) {
			request.setAttribute("cost", "cost contain only integer value");
			pass = false;

		}
		if (DataValidator.isNull(request.getParameter("purchaseDate"))) {
			request.setAttribute("purchaseDate", PropertyReader.getValue("error.require", "purchaseDate"));
			pass = false;
		} else if (!DataValidator.isDate(request.getParameter("purchaseDate"))) {
			request.setAttribute("purchaseDate", PropertyReader.getValue("error.date", "purchaseDate"));
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("category"))) {
			request.setAttribute("category", PropertyReader.getValue("error.require", "category"));
			pass = false;
		}

		/*
		 * else if (!DataValidator.isName(request.getParameter("category"))) {
		 * request.setAttribute("category", "customer  must contains alphabet only");
		 * pass = false; }
		 */

		return pass;

	}

	protected BaseDTO populateDTO(HttpServletRequest request) {
		ItemInformationDTO dto = new ItemInformationDTO();

		System.out.println(request.getParameter("dob"));
         
		dto.setId(DataUtility.getLong(request.getParameter("id")));
		dto.setTitle(DataUtility.getString(request.getParameter("title")));
		dto.setOverView(DataUtility.getString(request.getParameter("overView")));
		dto.setCost(DataUtility.getDouble(request.getParameter("cost")));

		dto.setPurchaseDate(DataUtility.getDate(request.getParameter("purchaseDate")));

		dto.setCategory(DataUtility.getString(request.getParameter("category")));

		populateBean(dto, request);

		return dto;

	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		String op = DataUtility.getString(request.getParameter("operation"));
		ItemInformationModelInt model = ModelFactory.getInstance().getItemInformationModel();
		long id = DataUtility.getLong(request.getParameter("id"));
		if (id > 0 || op != null) {
			ItemInformationDTO dto;
			try {
				dto = model.findByPK(id);
				ServletUtility.setDto(dto, request);
			} catch (Exception e) {
				e.printStackTrace();
				ServletUtility.handleException(e, request, response);
				return;
			}
		}
		ServletUtility.forward(getView(), request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		String op = DataUtility.getString(request.getParameter("operation"));
		ItemInformationModelInt model = ModelFactory.getInstance().getItemInformationModel();
		long id = DataUtility.getLong(request.getParameter("id"));
		if (OP_SAVE.equalsIgnoreCase(op) || OP_UPDATE.equalsIgnoreCase(op)) {
			ItemInformationDTO dto = (ItemInformationDTO) populateDTO(request);
			try {
				if (id > 0) {
					model.update(dto);

					ServletUtility.setSuccessMessage("Data is successfully Update", request);
				} else {

					try {
						model.add(dto);
						ServletUtility.setDto(dto, request);
						ServletUtility.setSuccessMessage("Data is successfully saved", request);
					} catch (ApplicationException e) {
						ServletUtility.handleException(e, request, response);
						return;
					} catch (DuplicateRecordException e) {
						ServletUtility.setDto(dto, request);
						ServletUtility.setErrorMessage("Login id already exists", request);
					}

				}

				ServletUtility.setDto(dto, request);

			} catch (ApplicationException e) {
				ServletUtility.handleException(e, request, response);
				return;
			} catch (DuplicateRecordException e) {
				ServletUtility.setDto(dto, request);
				ServletUtility.setErrorMessage("Login id already exists", request);
			}
		} else if (OP_DELETE.equalsIgnoreCase(op)) {

			ItemInformationDTO dto = (ItemInformationDTO) populateDTO(request);
			try {
				model.delete(dto);
				ServletUtility.redirect(ORSView.ITEMINFORMATION_LIST_CTL, request, response);
				return;
			} catch (ApplicationException e) {
				ServletUtility.handleException(e, request, response);
				return;
			}

		} else if (OP_CANCEL.equalsIgnoreCase(op)) {

			ServletUtility.redirect(ORSView.ITEMINFORMATION_LIST_CTL, request, response);
			return;
		} else if (OP_RESET.equalsIgnoreCase(op)) {

			ServletUtility.redirect(ORSView.ITEMINFORMATION_CTL, request, response);
			return;
		}
		ServletUtility.forward(getView(), request, response);

	}

	@Override
	protected String getView() {
		// TODO Auto-generated method stub
		return ORSView.ITEMINFORMATION_VIEW;
	}

}
