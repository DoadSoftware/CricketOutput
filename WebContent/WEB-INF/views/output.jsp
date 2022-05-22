<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html>
<html>
<head>

  <meta charset="utf-8" name="viewport" content="width=device-width, initial-scale=1">
  <title>Output Screen</title>
	
  <script type="text/javascript" src="<c:url value="/webjars/jquery/3.6.0/jquery.min.js"/>"></script>
  <script type="text/javascript" src="<c:url value="/webjars/bootstrap/5.1.3/js/bootstrap.min.js"/>"></script>
  <script type="text/javascript" src="<c:url value="/resources/javascript/index.js"/>"></script>
  
  <link rel="stylesheet" href="<c:url value="/webjars/bootstrap/5.1.3/css/bootstrap.min.css"/>"/>  
  <link href="<c:url value="/webjars/font-awesome/6.0.0/css/all.css"/>" rel="stylesheet">
	
</head>
<body>
<form:form name="output_form" autocomplete="off" action="POST">
<div class="content py-5" style="background-color: #EAE8FF; color: #2E008B">
  <div class="container">
	<div class="row">
	 <div class="col-md-8 offset-md-2">
       <span class="anchor"></span>
         <div class="card card-outline-secondary">
           <div class="card-header">
             <h3 class="mb-0">Output</h3>
           </div>
          <div class="card-body">
			  <div id="select_graphic_options_div" style="display:none;">
			  </div>
			  
			  <div id="captions_div" class="form-group row row-bottom-margin ml-2" style="margin-bottom:5px;">
			    <label class="col-sm-4 col-form-label text-left">Match: ${session_selected_match} </label>
			    <label class="col-sm-4 col-form-label text-left">IP Address: ${session_viz_ip_address} </label>
			    <label class="col-sm-4 col-form-label text-left">Port Number: ${session_viz_port_number} </label>
			    <label class="col-sm-4 col-form-label text-left">Broadcaster: ${session_selected_broadcaster} </label>
			    <label class="col-sm-4 col-form-label text-left">Home Team: ${session_match.homeTeam.fullname} </label>
			    <label class="col-sm-4 col-form-label text-left">Away Team: ${session_match.awayTeam.fullname} </label>
			   
  				<c:forEach var="inning" items="${session_match.inning}">
  					<label class="col-sm-4 col-form-label text-left">Current Inning:${inning.inningNumber}</label>
  					<label class="col-sm-4 col-form-label text-left">Total Runs:${inning.totalRuns} </label>
  					<label class="col-sm-4 col-form-label text-left">Total Overs:${inning.totalOvers} </label>
  					
  					<c:forEach var="battingcard" items="${inning.battingCard}">
  						<c:if test="${(battingcard.onStrike == 'YES')}">
							<label class="col-sm-4 col-form-label text-left">Batsman-OnStriker: ${battingcard.player.surname} </label>
						</c:if>
						<c:if test="${(battingcard.onStrike == 'NO')}">
							<label class="col-sm-4 col-form-label text-left">Batsman-NonStriker: ${battingcard.player.surname} </label>
						</c:if>
  					</c:forEach>
  					
  					<c:forEach var="bowlingcard" items="${inning.bowlingCard}">
						<c:choose>
							<c:when test="${(bowlingcard.status == 'CURRENTBOWLER')}">
								<label class="col-sm-4 col-form-label text-left">Current Bowler:${bowlingcard.player.surname}</label>
							</c:when>
							<c:otherwise>
								<c:if test="${(bowlingcard.status == 'LASTBOWLER')}">
									<label class="col-sm-4 col-form-label text-left">Current Bowler:${bowlingcard.player.surname}</label>
								</c:if>
							</c:otherwise>
							</c:choose>	
  					</c:forEach>
				</c:forEach>
				<div class="left">
				<button style="background-color:#2E008B;color:#FEFEFE;" class="btn btn-sm" type="button"
			  		name="infobar_graphic_btn" id="infobar_graphic_btn" onclick="processUserSelection(this)"> Infobar </button>
			    <button style="background-color:#2E008B;color:#FEFEFE;;" class="btn btn-sm" type="button"
			  		name="scorecard_graphic_btn" id="scorecard_graphic_btn" onclick="processUserSelection(this)"> ScoreCard </button> 
			    <button style="background-color:#2E008B;color:#FEFEFE;" class="btn btn-sm" type="button"
			  		name="bowlingcard_graphic_btn" id="bowlingcard_graphic_btn" onclick="processUserSelection(this)"> BowlingCard </button>
			 	<!--<button style="background-color:#2E008B;color:#FEFEFE;" class="btn btn-sm" type="button"
			  		name="partnership_graphic_btn" id="partnership_graphic_btn" onclick="processUserSelection(this)"> Partnership </button> -->
			  	<button style="background-color:#2E008B;color:#FEFEFE;" class="btn btn-sm" type="button"
			  		name="matchsummary_graphic_btn" id="matchsummary_graphic_btn" onclick="processUserSelection(this)"> Match Summary </button> 
			  	<button style="background-color:#2E008B;color:#FEFEFE;" class="btn btn-sm" type="button"
			  		name="bug_graphic_btn" id="bug_graphic_btn" onclick="processUserSelection(this)"> Bug </button>
			  	 <button style="background-color:#2E008B;color:#FEFEFE;" class="btn btn-sm" type="button"
			  		name="howout_graphic_btn" id="howout_graphic_btn" onclick="processUserSelection(this)"> How Out </button>
			  <!--	<button style="background-color:#2E008B;color:#FEFEFE;" class="btn btn-sm" type="button"
			  		name="doubleteams_graphic_btn" id="doubleteams_graphic_btn" onclick="processUserSelection(this)"> Double Teams </button> -->
			  	<button style="background-color:#f44336;color:#FEFEFE;;" class="btn btn-sm" type="button"
			  		name="animateout_graphic_btn" id="animateout_graphic_btn" onclick="processUserSelection(this)"> AnimateOut </button>
			  </div>
			  </div>
	       </div>
	    </div>
       </div>
    </div>
  </div>
</div>
<input type="hidden" name="selected_broadcaster" id="selected_broadcaster" value="${session_selected_broadcaster}"/>
<input type="hidden" name="selected_match_max_overs" id="selected_match_max_overs" value="${session_match.maxOvers}"/>
<input type="hidden" id="matchFileTimeStamp" name="matchFileTimeStamp" value="${session_match.matchFileTimeStamp}"></input>
</form:form>
</body>
</html>