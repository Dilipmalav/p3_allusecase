package in.co.rays.project_3.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import in.co.rays.project_3.dto.BaseDTO;
import in.co.rays.project_3.dto.MedicationDTO;
import in.co.rays.project_3.exception.ApplicationException;
import in.co.rays.project_3.exception.DuplicateRecordException;
import in.co.rays.project_3.model.ModelFactory;
import in.co.rays.project_3.model.MedicationModelInt;
import in.co.rays.project_3.util.DataUtility;
import in.co.rays.project_3.util.DataValidator;
import in.co.rays.project_3.util.PropertyReader;
import in.co.rays.project_3.util.ServletUtility;

@WebServlet(name = "MedicationCtl", urlPatterns = { "/ctl/MedicationCtl" })

public class MedicationCtl extends BaseCtl {

	@Override
	protected void preload(HttpServletRequest request) {

		Map<Integer, String> map = new HashMap();
		map.put(1, "Diabetes");
		map.put(2, "Asthma");
		map.put(3, "Dengue");
		map.put(4, "Malaria");
		map.put(5, "Depression");

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

		if (DataValidator.isNull(request.getParameter("fullName"))) {
			request.setAttribute("fullName", PropertyReader.getValue("error.require", "fullName"));
			pass = false;

		} else if (!DataValidator.isName(request.getParameter("fullName"))) {
			request.setAttribute("fullName", "only letter are allowed ");
			pass = false;
		}

		else if (request.getParameter("fullName").length() <= 2 || request.getParameter("fullName").length() >= 15) {
			request.setAttribute("fullName", " fullName bteween 2 to 15");
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("illness"))) {
			request.setAttribute("illness", PropertyReader.getValue("error.require", "illness"));
			pass = false;
		}

		/*
		 * else if (!DataValidator.isName(request.getParameter("illness"))) {
		 * request.setAttribute("illness", "illness contain only String value"); pass =
		 * false;
		 * 
		 * }
		 */
		if (DataValidator.isNull(request.getParameter("prescriptionDate"))) {
			request.setAttribute("prescriptionDate", PropertyReader.getValue("error.require", "prescriptionDate"));
			pass = false;
		} else if (!DataValidator.isDate(request.getParameter("prescriptionDate"))) {
			request.setAttribute("prescriptionDate", PropertyReader.getValue("error.date", "prescriptionDate"));
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("dosage"))) {
			request.setAttribute("dosage", PropertyReader.getValue("error.require", "dosage"));
			pass = false;
		}
          else if (!DataValidator.validateAlphanumeric(request.getParameter("dosage"))) {
		  request.setAttribute("dosage", "dosage  must contains alphanumric only");
		  pass = false;
		  }
		 

		return pass;

	}

	protected BaseDTO populateDTO(HttpServletRequest request) {
		MedicationDTO dto = new MedicationDTO();

		System.out.println(request.getParameter("dob"));

		dto.setId(DataUtility.getLong(request.getParameter("id")));
		dto.setFullName(DataUtility.getString(request.getParameter("fullName")));

		dto.setIllness(DataUtility.getString(request.getParameter("illness")));

		dto.setPrescriptionDate(DataUtility.getDate(request.getParameter("prescriptionDate")));

		dto.setDosage(DataUtility.getString(request.getParameter("dosage")));


		populateBean(dto, request);

		return dto;

	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		String op = DataUtility.getString(request.getParameter("operation"));
		MedicationModelInt model = ModelFactory.getInstance().getMedicationModel();
		long id = DataUtility.getLong(request.getParameter("id"));
		if (id > 0 || op != null) {
			MedicationDTO dto;
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
		MedicationModelInt model = ModelFactory.getInstance().getMedicationModel();
		long id = DataUtility.getLong(request.getParameter("id"));
		if (OP_SAVE.equalsIgnoreCase(op) || OP_UPDATE.equalsIgnoreCase(op)) {
			MedicationDTO dto = (MedicationDTO) populateDTO(request);
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

			MedicationDTO dto = (MedicationDTO) populateDTO(request);
			try {
				model.delete(dto);
				ServletUtility.redirect(ORSView.MEDICATION_LIST_CTL, request, response);
				return;
			} catch (ApplicationException e) {
				ServletUtility.handleException(e, request, response);
				return;
			}

		} else if (OP_CANCEL.equalsIgnoreCase(op)) {

			ServletUtility.redirect(ORSView.MEDICATION_LIST_CTL, request, response);
			return;
		} else if (OP_RESET.equalsIgnoreCase(op)) {

			ServletUtility.redirect(ORSView.MEDICATION_CTL, request, response);
			return;
		}
		ServletUtility.forward(getView(), request, response);

	}

	@Override
	protected String getView() {
		// TODO Auto-generated method stub
		return ORSView.MEDICATION_VIEW;
	}

}
