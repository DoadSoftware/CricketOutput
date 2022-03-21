function processWaitingButtonSpinner(whatToProcess) 
{
	switch (whatToProcess) {
	case 'START_WAIT_TIMER': 
		$('.spinner-border').show();
		$(':button').prop('disabled', true);
		break;
	case 'END_WAIT_TIMER': 
		$('.spinner-border').hide();
		$(':button').prop('disabled', false);
		break;
	}
	
}
function reloadPage(whichPage)
{
	switch (whichPage) {
	case 'initialise':
		processUserSelection($('#select_broadcaster'));
		break;
	}
}
function processUserSelection(whichInput)
{	
	switch ($(whichInput).attr('name')) {
	case 'scorecard_graphic_btn': case 'bowlingcard_graphic_btn':
		$("#captions_div").hide();
		switch ($(whichInput).attr('name')) {
		case 'scorecard_graphic_btn': 
			addItemsToList('SCORECARD-OPTIONS',null);
			break;
		case 'bowlingcard_graphic_btn':
			addItemsToList('BOWLINGCARD-OPTIONS',null);
			break;
		}
		break;
	case 'populate_scorecard_btn': case 'populate_bowlingcard_btn':
		processWaitingButtonSpinner('START_WAIT_TIMER');
		switch ($(whichInput).attr('name')) {
		case 'populate_scorecard_btn':
			processCricketProcedures('POPULATE-SCORECARD');
			break;
		case 'populate_bowlingcard_btn':
			processCricketProcedures('POPULATE-BOWLINGCARD');
			break;
		}
		break;
	case 'cancel_graphics_btn':
		$('#select_graphic_options_div').empty();
		document.getElementById('select_graphic_options_div').style.display = 'none';
		$("#captions_div").show();
		break;
	case 'select_broadcaster':
		switch ($('#select_broadcaster :selected').val().toUpperCase()) {
		case 'DOAD':
			$('#vizScene').attr('value','/Default/DOAD_In_House/BatBallSummary');
			break;
		}
		break;
	case 'load_scene_btn':
		if(checkEmpty($('#vizIPAddress'),'IP Address Blank') == false
			|| checkEmpty($('#vizPortNumber'),'Port Number Blank') == false
			|| checkEmpty($('#vizScene'),'Scene Path Blank') == false) {
			return false;
		}
      	document.initialise_form.submit();
		break;
	case 'bug_graphic_btn':
		addItemsToList('BUG-OPTIONS',null);
		break;
	}
}
function processCricketProcedures(whatToProcess)
{
	var valueToProcess;
	
	switch(whatToProcess) {
	case 'READ-MATCH-AND-POPULATE':
		valueToProcess = $('#match_file_timestamp').val();
		break;
	case 'POPULATE-SCORECARD': case 'POPULATE-BOWLINGCARD':
		switch ($('#selected_broadcaster').val().toUpperCase()) {
		case 'DOAD':
			valueToProcess = $('#selectInning').find(":selected").val();
			break;
		}
		break;
	}

	$.ajax({    
        type : 'Get',     
        url : 'processCricketProcedures.html',     
        data : 'whatToProcess=' + whatToProcess + '&valueToProcess=' + valueToProcess, 
        dataType : 'json',
        success : function(data) {
        	switch(whatToProcess) {
			case 'POPULATE-SCORECARD': case 'POPULATE-BOWLINGCARD':
				if (data.status.toUpperCase() == 'SUCCESS') {
		        	switch(whatToProcess) {
					case 'POPULATE-SCORECARD': 
						$('#populate_scorecard_btn').hide();
						$('#animate_in_scorecard_btn').show();
						break;
					case 'POPULATE-BOWLINGCARD':
						$('#populate_bowlingcard_btn').hide();
						$('#animate_in_bowlingcard_btn').show();
						break;
					}
				} else {
					alert(data.status);
				}
				break;
        	}
			processWaitingButtonSpinner('END_WAIT_TIMER');
	    },    
	    error : function(e) {    
	  	 	console.log('Error occured in ' + whatToProcess + ' with error description = ' + e);     
	    }    
	});
}
function addItemsToList(whatToProcess, dataToProcess)
{
	var select,option,header_text,div,table,tbody,row,max_cols;
	
	switch (whatToProcess) {
	case 'SCORECARD-OPTIONS': case'BOWLINGCARD-OPTIONS':
	
		switch ($('#selected_broadcaster').val().toUpperCase()) {
		case 'DOAD':

			$('#select_graphic_options_div').empty();
	
			header_text = document.createElement('h6');
			header_text.innerHTML = 'Select Scorecard Options';
			document.getElementById('select_graphic_options_div').appendChild(header_text);
			
			table = document.createElement('table');
			table.setAttribute('class', 'table table-bordered');
					
			tbody = document.createElement('tbody');
	
			table.appendChild(tbody);
			document.getElementById('select_graphic_options_div').appendChild(table);
			
			row = tbody.insertRow(tbody.rows.length);

			select = document.createElement('select');
			select.id = 'selectInning';
			if(document.getElementById('selected_match_max_overs').value > 0) {
				max_cols = 2;
			} else {
				max_cols = 4;
			}
			for(var i=1; i<=max_cols; i++) {
				option = document.createElement('option');
				option.value = i;
			    option.text = 'Inning ' + i;
			    select.appendChild(option);
			}

			row.insertCell(0).appendChild(select);
	
		    option = document.createElement('input');
		    option.type = 'button';
			switch (whatToProcess) {
			case 'SCORECARD-OPTIONS': 
			    option.name = 'populate_scorecard_btn';
			    option.value = 'Populate Scorecard';
				break;
			case'BOWLINGCARD-OPTIONS':
			    option.name = 'populate_bowlingcard_btn';
			    option.value = 'Populate Bowlingcard';
				break;
			}
		    option.id = option.name;
		    option.setAttribute('onclick',"processUserSelection(this)");
		    
		    div = document.createElement('div');
		    div.append(option);

		    option = document.createElement('input');
		    option.type = 'button';
			switch (whatToProcess) {
			case 'SCORECARD-OPTIONS': 
			    option.name = 'animate_in_scorecard_btn';
			    option.value = 'Animate In Scorecard';
				break;
			case'BOWLINGCARD-OPTIONS':
			    option.name = 'animate_in_bowlingcard_btn';
			    option.value = 'Animate In Bowlingcard';
				break;
			}
		    option.id = option.name;
		    option.style.display = 'none';
		    option.setAttribute('onclick',"processUserSelection(this)");
		    
		    div.append(option);

			option = document.createElement('input');
			option.type = 'button';
			option.name = 'cancel_graphics_btn';
			option.id = option.name;
			option.value = 'Cancel';
			option.setAttribute('onclick','processUserSelection(this)');
	
		    div.append(option);
		    
		    row.insertCell(1).appendChild(div);
		    
			document.getElementById('select_graphic_options_div').style.display = '';

			break;
		}
		break;
	}
}
function checkEmpty(inputBox,textToShow) {

	var name = $(inputBox).attr('id');
	
	document.getElementById(name + '-validation').innerHTML = '';
	document.getElementById(name + '-validation').style.display = 'none';
	$(inputBox).css('border','');
	if(document.getElementById(name).value.trim() == '') {
		$(inputBox).css('border','#E11E26 2px solid');
		document.getElementById(name + '-validation').innerHTML = textToShow + ' required';
		document.getElementById(name + '-validation').style.display = '';
		document.getElementById(name).focus({preventScroll:false});
		return false;
	}
	return true;	
}