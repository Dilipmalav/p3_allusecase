<%@page import="in.co.rays.project_3.controller.PortfolioManagementCtl"%>
<%@page import="java.util.Map"%>
<%@page import="java.util.List"%>
<%@page import="java.util.HashMap"%>
<%@page import="in.co.rays.project_3.util.HTMLUtility"%>
<%@page import="in.co.rays.project_3.util.DataUtility"%>
<%@page import="in.co.rays.project_3.util.ServletUtility"%>
<%@page import="in.co.rays.project_3.controller.ORSView"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title></title>
<meta name="viewport" content="width=device-width, initial-scale=1">
<script src="<%=ORSView.APP_CONTEXT%>/js/utilities.js"></script>
<script src="<%=ORSView.APP_CONTEXT%>/js/ValidateToInput.js"></script>
<style type="text/css">
i.css {
	border: 2px solid #8080803b;
	padding-left: 10px;
	padding-bottom: 11px;
	background-color: #ebebe0;
}

.input-group-addon {
	/* box-shadow: 9px 8px 7px #001a33; */
	background-image: linear-gradient(to bottom right, orange, black);
	background-repeat: no repeat;
	background-size: 100%;
	padding-bottom: 11px;
}

.hm {
	background-image: url('<%=ORSView.APP_CONTEXT%>/img/unsplash.jpg');
	background-size: cover;
	padding-top: 6%;
}
</style>

</head>


<script>
	function limitInputLength(textarea, errorElementId, minLength, maxLength) {
		let errorElement = document.getElementById(errorElementId);
		let value = textarea.value.trim(); // Trim spaces to avoid fake length validation

		if (value.length < minLength) {
			errorElement.innerText = `Minimum ${minLength} characters required.`;
		} else if (value.length > maxLength) {
			textarea.value = value.slice(0, maxLength); // Trim extra characters
			errorElement.innerText = `Maximum ${maxLength} characters allowed.`;
		} else {
			errorElement.innerText = ""; // Clear error when within valid range
		}
	}
</script>



<body class="hm">
	<div class="header">
		<%@include file="Header.jsp"%>
		<%@include file="calendar.jsp"%>
	</div>
	<div>

		<main>
		<form action="<%=ORSView.PORTFOLIOMANAGMENT_CTL%>" method="post">
			<jsp:useBean id="dto"
				class="in.co.rays.project_3.dto.PortfolioManagementDTO"
				scope="request"></jsp:useBean>
			<div class="row pt-3">
				<!-- Grid column -->
				<div class="col-md-4 mb-4"></div>
				<div class="col-md-4 mb-4">
					<div class="card input-group-addon">
						<div class="card-body">

							<%
								long id = DataUtility.getLong(request.getParameter("id"));

								if (dto.getId() != null && id > 0) {
							%>
							<h3 class="text-center default-text text-primary">Update
								PortfolioManagement</h3>
							<%
								} else {
							%>
							<h3 class="text-center default-text text-primary">Add
								PortfolioManagement</h3>
							<%
								}
							%>
							<!--Body-->
							<div>
								<%
									List list = (List) request.getAttribute("roleList");
									List list1 = (List) request.getAttribute("list1");
									Map map = (Map) request.getAttribute("productt");
								%>

								<H4 align="center">
									<%
										if (!ServletUtility.getSuccessMessage(request).equals("")) {
									%>
									<div class="alert alert-success alert-dismissible">
										<button type="button" class="close" data-dismiss="alert">&times;</button>
										<%=ServletUtility.getSuccessMessage(request)%>
									</div>
									<%
										}
									%>
								</H4>

								<H4 align="center">
									<%
										if (!ServletUtility.getErrorMessage(request).equals("")) {
									%>
									<div class="alert alert-danger alert-dismissible">
										<button type="button" class="close" data-dismiss="alert">&times;</button>
										<%=ServletUtility.getErrorMessage(request)%>
									</div>
									<%
										}
									%>

								</H4>

								<input type="hidden" name="id" value="<%=dto.getId()%>">

							</div>


							<span class="pl-sm-5"><b>PortfolioName</b> <span
								style="color: red;">*</span></span> </br>
							<div class="col-sm-12">
								<div class="input-group">
									<div class="input-group-prepend">
										<div class="input-group-text">
											<i class="fa fa-user-alt grey-text" style="font-size: 1rem;"></i>
										</div>
									</div>
									<input type="text" class="form-control" name="portfolioName"
										placeholder="Enter portfolioName"
										oninput="handleLetterInput(this, 'portfolioNameError', 25)"
										onblur="validateLetterInput(this, 'portfolioNameError', 25)"
										value="<%=(DataUtility.getStringData(dto.getPortfolioName()))%>">

								</div>
							</div>
							<font color="red" class="pl-sm-5" id="portfolioNameError">
								<%=ServletUtility.getErrorMessage("portfolioName", request)%></font></br> <span
								class="pl-sm-5"><b>InitialInvestmentAmount</b> <span
								style="color: red;">*</span></span> </br>
							<div class="col-sm-12">
								<div class="input-group">
									<div class="input-group-prepend">
										<div class="input-group-text">
											<i class="fa fa-user-alt grey-text" style="font-size: 1rem;"></i>
										</div>
									</div>
									<input type="text" class="form-control"
										name="initialInvestmentAmount"
										placeholder="Enter initialInvestmentAmount"
										oninput=" handleDoubleInput(this, 'initialInvestmentAmountError', 25)"
										onblur="validateDoubleInput(this, 'initialInvestmentAmountError', 25)"
										value="<%=DataUtility.getStringData(dto.getInitialInvestmentAmount()).equals("0.0") ? ""
					: DataUtility.getStringData(dto.getInitialInvestmentAmount())%>">

								</div>
							</div>
							<font color="red" class="pl-sm-5"
								id="initialInvestmentAmountError"> <%=ServletUtility.getErrorMessage("initialInvestmentAmount", request)%></font></br>







							<span class="pl-sm-5"><b>RiskToleranceLevel</b><span
								style="color: red;">*</span></span> </br>

							<div class="col-sm-12">
								<div class="input-group">
									<div class="input-group-prepend">
										<div class="input-group-text">
											<i class="fa fa-venus-mars grey-text"
												style="font-size: 1rem;"></i>
										</div>
									</div>

									<%=HTMLUtility.getList1("riskToleranceLevel", String.valueOf(dto.getRiskToleranceLevel()), map)%>
								</div>
							</div>
							<font color="red" class="pl-sm-5"> <%=ServletUtility.getErrorMessage("riskToleranceLevel", request)%></font></br>



							<span class="pl-sm-5"><b>Investment Strategy</b> <span
								style="color: red;">*</span></span></br>
							<div class="col-sm-12">
								<div class="input-group">
									<div class="input-group-prepend">
										<div class="input-group-text">
											<i class="fa fa-info-circle grey-text"
												style="font-size: 1rem;"></i>
										</div>
									</div>
									<textarea name="investmentStrategy"
										placeholder="Enter Investment Strategy" rows="5" cols="5"
										class="form-control"
										oninput="limitInputLength(this, 'investmentStrategyError', 10, 200)"
										id="investmentStrategy"><%=(dto.getInvestmentStrategy() != null && !dto.getInvestmentStrategy().trim().isEmpty())
					? DataUtility.getStringData(dto.getInvestmentStrategy())
					: ""%></textarea>
								</div>
							</div>
							<font color="red" class="pl-sm-5" id="investmentStrategyError">
								<%=ServletUtility.getErrorMessage("investmentStrategy", request)%>
							</font></br>




							<%
								if (dto.getId() != null && id > 0) {
							%>

							<div class="text-center">

								<input type="submit" name="operation"
									class="btn btn-success btn-md" style="font-size: 17px"
									value="<%=PortfolioManagementCtl.OP_UPDATE%>"> <input
									type="submit" name="operation" class="btn btn-warning btn-md"
									style="font-size: 17px"
									value="<%=PortfolioManagementCtl.OP_CANCEL%>">

							</div>
							<%
								} else {
							%>
							<div class="text-center">

								<input type="submit" name="operation"
									class="btn btn-success btn-md" style="font-size: 17px"
									value="<%=PortfolioManagementCtl.OP_SAVE%>"> <input
									type="submit" name="operation" class="btn btn-warning btn-md"
									style="font-size: 17px"
									value="<%=PortfolioManagementCtl.OP_RESET%>">
							</div>

						</div>
						<%
							}
						%>
					</div>
				</div>
		</form>
		</main>
		<div class="col-md-4 mb-4"></div>

	</div>

</body>
<%@include file="FooterView.jsp"%>

</body>
</html>