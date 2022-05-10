var match_data;
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
	case 'animateout_graphic_btn':
		if(confirm('It will Also Delete Your Preview from Directory...\r\n \r\nAre You Sure To Animate Out? ') == true){
			processCricketProcedures('ANIMATE-OUT');	
		}
		break;
	case 'scorecard_graphic_btn': case 'bowlingcard_graphic_btn': case 'partnership_graphic_btn': case 'matchsummary_graphic_btn': case 'bug_graphic_btn': case 'howout_graphic_btn': case 'doubleteams_graphic_btn':
		$("#captions_div").hide();
		switch ($(whichInput).attr('name')) {
		case 'scorecard_graphic_btn': 
			addItemsToList('SCORECARD-OPTIONS',null);
			break;
		case 'bowlingcard_graphic_btn':
			addItemsToList('BOWLINGCARD-OPTIONS',null);
			break;
		case 'partnership_graphic_btn':
			addItemsToList('PARTNERSHIP-OPTIONS',null);
			break;
		case 'matchsummary_graphic_btn':
			addItemsToList('MATCHSUMMARY-OPTIONS',null);
			break;
		case 'bug_graphic_btn':
			processCricketProcedures('GRAPHICS-OPTIONS');
			break;
		case 'howout_graphic_btn':
			processCricketProcedures('GRAPHIC-OPTIONS');
			break;
		case 'doubleteams_graphic_btn':
			addItemsToList('DOUBLETEAMS-OPTIONS',null);
			break;
		}
		break;
	case 'populate_scorecard_btn': case 'populate_bowlingcard_btn': case 'populate_partnership_btn': case 'populate_matchsummary_btn': case 'populate_bug_btn': case 'populate_howout_btn': case 'populate_doubleteams_btn':
		processWaitingButtonSpinner('START_WAIT_TIMER');
		switch ($(whichInput).attr('name')) {
		case 'populate_scorecard_btn':
			processCricketProcedures('POPULATE-SCORECARD');
			break;
		case 'populate_bowlingcard_btn':
			processCricketProcedures('POPULATE-BOWLINGCARD');
			break;
		case 'populate_partnership_btn':
			processCricketProcedures('POPULATE-PARTNERSHIP');
			break;
		case 'populate_matchsummary_btn':
			processCricketProcedures('POPULATE-MATCHSUMMARY');
			break;
		case 'populate_bug_btn':
			processCricketProcedures('POPULATE-BUG');
			break;
		case 'populate_howout_btn':
			processCricketProcedures('POPULATE-HOWOUT');
			break;
		case 'populate_doubleteams_btn':
			processCricketProcedures('POPULATE-DOUBLETEAMS');
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
			//$('#vizScene').attr('value','/Default/DOAD_In_House/BatBallSummary');
			//$('#vizScene').attr('value','/Default/DOAD_In_House/Bugs');
			//$('#vizScene').attr('value','/Default/DOAD_In_House/Lt_HowOut');
			$('#vizScene').attr('value','/Default/DOAD_In_House/TeamLineUp');
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
	/*case 'selectInning': case 'selectStatsType':
		switch ($(whichInput).attr('name')) {
		case 'selectInning':
			addItemsToList('POPULATE-PLAYERS',match_data);
			break;
		case 'selectStatsType':
			addItemsToList('POPULATE-PLAYERS',match_data);
			break;
		}
		break;*/
	}
}
function processCricketProcedures(whatToProcess)
{
	var valueToProcess;
	switch(whatToProcess) {
	case 'READ-MATCH-AND-POPULATE':
		valueToProcess = $('#matchFileTimeStamp').val();
		break;
	case 'POPULATE-SCORECARD': case 'POPULATE-BOWLINGCARD': case 'POPULATE-PARTNERSHIP': case 'POPULATE-MATCHSUMMARY': 
		switch ($('#selected_broadcaster').val().toUpperCase()) {
		case 'DOAD':
			valueToProcess = $('#selectInning option:selected').val();
			break;	
		}
		break;
	case 'POPULATE-BUG':
		switch ($('#selected_broadcaster').val().toUpperCase()) {
		case 'DOAD':
			valueToProcess = $('#selectInning option:selected').val() + ',' + $('#selectStatsType option:selected').val() + ',' + $('#selectPlayers option:selected').val() ;
			break;
		}
		break;
	case 'POPULATE-HOWOUT':
		switch ($('#selected_broadcaster').val().toUpperCase()) {
		case 'DOAD':
			valueToProcess = $('#selectInning option:selected').val() + ',' + $('#selectStatsType option:selected').val() + ',' + $('#selectPlayers option:selected').val() ;
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
			case 'READ-MATCH-AND-POPULATE':
				addItemsToList(whatToProcess,data);
				$('#matchFileTimeStamp').val(data.matchFileTimeStamp);
				break;
			case 'GRAPHICS-OPTIONS':
				addItemsToList('BUG-OPTIONS',data);
				addItemsToList('POPULATE-PLAYERS',data);
				match_data = data;
				break;
			case 'GRAPHIC-OPTIONS':
				addItemsToList('HOWOUT-OPTIONS',data);
				addItemsToList('POPULATE-PLAYERS',data);
				match_data = data;
				break;
			case 'POPULATE-SCORECARD': case 'POPULATE-BOWLINGCARD': case 'POPULATE-PARTNERSHIP': case 'POPULATE-MATCHSUMMARY': case 'POPULATE-BUG': case 'POPULATE-HOWOUT': case 'POPULATE-DOUBLETEAMS':
				if (data.status.toUpperCase() == 'SUCCESSFUL') {
					if(confirm('Animate In?') == true){
						
						$('#select_graphic_options_div').empty();
						document.getElementById('select_graphic_options_div').style.display = 'none';
						$("#captions_div").show();
						
			        	switch(whatToProcess) {
						case 'POPULATE-SCORECARD': 
							processCricketProcedures('ANIMATE-IN-SCORECARD');
							break;
						case 'POPULATE-BOWLINGCARD':
							processCricketProcedures('ANIMATE-IN-BOWLINGCARD');					
							break;
						case 'POPULATE-PARTNERSHIP':
							processCricketProcedures('ANIMATE-IN-PARTNERSHIP');					
							break;
						case 'POPULATE-MATCHSUMMARY':
							processCricketProcedures('ANIMATE-IN-MATCHSUMARRY');			
							break;
						case 'POPULATE-BUG':
							processCricketProcedures('ANIMATE-IN-BUG');				
							break;
						case 'POPULATE-HOWOUT':
							processCricketProcedures('ANIMATE-IN-HOWOUT');				
							break;
						case 'POPULATE-DOUBLETEAMS':
							processCricketProcedures('ANIMATE-IN-DOUBLETEAMS');				
							break;
					}
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
	var cellCount = 0;

	switch (whatToProcess) {
	/*case 'POPULATE-PLAYERS' :
	
		$('#selectPlayers').empty();

		dataToProcess.inning.forEach(function(inn,index,arr){
			if(inn.inningNumber == $('#selectInning option:selected').val()){
				if($('#selectStatsType option:selected').val() == 'Batsman'){
					inn.battingCard.forEach(function(bc,bc_index,bc_arr){
			            $('#selectPlayers').append(
							$(document.createElement('option')).prop({
			                value: bc.playerId,
			                text: bc.player.full_name
			            }))						
					});
				} else{
					inn.bowlingCard.forEach(function(boc,boc_index,boc_arr){
			            $('#selectPlayers').append(
							$(document.createElement('option')).prop({
			                value: boc.playerId,
			                text: boc.player.full_name
			            }))						
					});
				}	
			}
		});
		
		break;*/
 	
	case 'SCORECARD-OPTIONS': case'BOWLINGCARD-OPTIONS': case'PARTNERSHIP-OPTIONS': case'MATCHSUMMARY-OPTIONS': case'BUG-OPTIONS': case'HOWOUT-OPTIONS':
	
		switch ($('#selected_broadcaster').val().toUpperCase()) {
		case 'DOAD':

			$('#select_graphic_options_div').empty();
	
			header_text = document.createElement('h6');
			header_text.innerHTML = 'Select Graphic Options';
			document.getElementById('select_graphic_options_div').appendChild(header_text);
			
			table = document.createElement('table');
			table.setAttribute('class', 'table table-bordered');
					
			tbody = document.createElement('tbody');
	
			table.appendChild(tbody);
			document.getElementById('select_graphic_options_div').appendChild(table);
			
			row = tbody.insertRow(tbody.rows.length);

			select = document.createElement('select');
			select.id = 'selectInning';
			select.name = select.id;
			
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
			row.insertCell(cellCount).appendChild(select);
			cellCount = cellCount + 1;
			
			switch(whatToProcess){
			case'BUG-OPTIONS':

			    select.setAttribute('onchange',"processUserSelection(this)");

				select = document.createElement('select');
				select.id = 'selectStatsType';
				select.name = select.id;
				
				option = document.createElement('option');
				option.value = 'Batsman';
				option.text = 'Batsman';
				select.appendChild(option);
				
				option = document.createElement('option');
				option.value = 'Bowler';
				option.text = 'Bowler';
				select.appendChild(option);
				
			    select.setAttribute('onchange',"processUserSelection(this)");
				row.insertCell(cellCount).appendChild(select);
				
				cellCount = cellCount + 1;
				
				select = document.createElement('select');
				select.id = 'selectPlayers';
				select.name = select.id;
				
				row.insertCell(cellCount).appendChild(select);

				cellCount = cellCount + 1;
				
				break;
				
			case'HOWOUT-OPTIONS':
			
				select.setAttribute('onchange',"processUserSelection(this)");
				
				select = document.createElement('select');
				select.id = 'selectHowOutBatsmen';
				select.name = select.id;
				
				option = document.createElement('option');
				option.value = 'Batsman';
				option.text = 'Batsman';
				select.appendChild(option);
				
				select.setAttribute('onchange',"processUserSelection(this)");
				row.insertCell(cellCount).appendChild(select);
				
				cellCount = cellCount + 1;
				
				select = document.createElement('select');
				select.id = 'selectPlayers';
				select.name = select.id;
				
				row.insertCell(cellCount).appendChild(select);

				cellCount = cellCount + 1;
				break;
			}
			
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
			case'PARTNERSHIP-OPTIONS':
			    option.name = 'populate_partnership_btn';
			    option.value = 'Populate Partnership';
				break;
			case'MATCHSUMMARY-OPTIONS':
			    option.name = 'populate_matchsummary_btn';
			    option.value = 'Populate Matchsummary';
				break;
			case'BUG-OPTIONS':
			    option.name = 'populate_bug_btn';
			    option.value = 'Populate Bug';
				break;
			case'HOWOUT-OPTIONS':
			    option.name = 'populate_howout_btn';
			    option.value = 'Populate Howout';
				break;
			}
		    option.id = option.name;
		    option.setAttribute('onclick',"processUserSelection(this)");
		    
		    div = document.createElement('div');
		    div.append(option);

			option = document.createElement('input');
			option.type = 'button';
			option.name = 'cancel_graphics_btn';
			option.id = option.name;
			option.value = 'Cancel';
			option.setAttribute('onclick','processUserSelection(this)');
	
		    div.append(option);
		    
		    row.insertCell(cellCount).appendChild(div);
		    cellCount = cellCount + 1;
		    
			document.getElementById('select_graphic_options_div').style.display = '';

			break;
		}
		break;
		
	case'DOUBLETEAMS-OPTIONS':
	
		switch ($('#selected_broadcaster').val().toUpperCase()) {
		case 'DOAD':

			$('#select_graphic_options_div').empty();
	
			header_text = document.createElement('h6');
			header_text.innerHTML = 'Select Graphic Options';
			document.getElementById('select_graphic_options_div').appendChild(header_text);
			
			table = document.createElement('table');
			table.setAttribute('class', 'table table-bordered');
					
			tbody = document.createElement('tbody');
	
			table.appendChild(tbody);
			document.getElementById('select_graphic_options_div').appendChild(table);
			
			row = tbody.insertRow(tbody.rows.length);

			option = document.createElement('input');
		    option.type = 'button';
		    
			switch (whatToProcess) {
			case'DOUBLETEAMS-OPTIONS':
			    option.name = 'populate_doubleteams_btn';
			    option.value = 'Populate Doubleteams';
				break;
			}
		    option.id = option.name;
		    option.setAttribute('onclick',"processUserSelection(this)");
		    
		    div = document.createElement('div');
		    div.append(option);

			option = document.createElement('input');
			option.type = 'button';
			option.name = 'cancel_graphics_btn';
			option.id = option.name;
			option.value = 'Cancel';
			option.setAttribute('onclick','processUserSelection(this)');
	
		    div.append(option);
		    
		    row.insertCell(cellCount).appendChild(div);
		    cellCount = cellCount + 1;
		    
			document.getElementById('select_graphic_options_div').style.display = '';

			break;
		}	
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