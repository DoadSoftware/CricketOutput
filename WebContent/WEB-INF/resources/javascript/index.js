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
	case 'scorecard_graphic_btn': case 'bowlingcard_graphic_btn': case 'partnership_graphic_btn': case 'matchsummary_graphic_btn': case 'bug_graphic_btn':
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
		}
		break;
	case 'populate_scorecard_btn': case 'populate_bowlingcard_btn': case 'populate_partnership_btn': case 'populate_matchsummary_btn': case 'populate_bug_btn':
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
	case 'selectInning': case 'selectStatsType':
		switch ($(whichInput).attr('name')) {
		case 'selectInning':
			addItemsToList('BUG-OPTIONS',null);
			break;
		case 'selectStatsType':
			addItemsToList('BUG-OPTIONS',null);
			break;
			}
		break;
	/*case 'bug_graphic_btn':
		addItemsToList('BUG-OPTIONS',null);
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
	case 'POPULATE-SCORECARD': case 'POPULATE-BOWLINGCARD': case 'POPULATE-PARTNERSHIP': case 'POPULATE-MATCHSUMMARY': case 'POPULATE-BUG':
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
			case 'READ-MATCH-AND-POPULATE':
				addItemsToList(whatToProcess,data);
				$('#matchFileTimeStamp').val(data.matchFileTimeStamp);
				break;
			case 'GRAPHICS-OPTIONS':
				addItemsToList('BUG-OPTIONS',data);
				break;
			case 'POPULATE-SCORECARD': case 'POPULATE-BOWLINGCARD': case 'POPULATE-PARTNERSHIP': case 'POPULATE-MATCHSUMMARY': case 'POPULATE-BUG':
			//$('#matchFileTimeStamp').val(session_match.matchFileTimeStamp);
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
					case 'POPULATE-PARTNERSHIP':
						$('#populate_partnership_btn').hide();
						$('#animate_in_partnership_btn').show();
						break;
					case 'POPULATE-MATCHSUMMARY':
						$('#populate_matchsummary_btn').hide();
						$('#animate_in_matchsummary_btn').show();
						break;
					case 'POPULATE-BUG':
						$('#populate_bug_btn').hide();
						$('#animate_in_bug_btn').show();
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
	var cellCount = 0;
	switch (whatToProcess) {
	case 'SCORECARD-OPTIONS': case'BOWLINGCARD-OPTIONS': case'PARTNERSHIP-OPTIONS': case'MATCHSUMMARY-OPTIONS': case'BUG-OPTIONS':
	
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
			    option.onChange="processUserSelection(this);";
			    select.appendChild(option);
			}
			//select.setAttribute('onclick',"processUserSelection(this)");
			//select.onchange="processUserSelection(this)";
			//$("#selectInning").onchange=('processUserSelection(this);');
			row.insertCell(cellCount).appendChild(select);
			cellCount = cellCount + 1;
			
			
			switch(whatToProcess){
			case'BUG-OPTIONS':
				select = document.createElement('select');
				select.id = 'selectStatsType';
				
				option = document.createElement('option');
				option.value = 'Batsman';
				option.text = 'Batsman';
				option.onChange="processUserSelection(this);";
				select.appendChild(option);
				
				option = document.createElement('option');
				option.value = 'Bowler';
				option.text = 'Bowler';
				option.onChange=('processUserSelection(this);')
				select.appendChild(option);
				
				//select.setAttribute('onclick',"processUserSelection(this)");
				//select.onchange="processUserSelection(this)";
				//$("#selectStatsType").onchange=('processUserSelection(this)')
				row.insertCell(cellCount).appendChild(select);
				cellCount = cellCount + 1;
				
				select = document.createElement('select');
				select.id = 'selectPlayer';
				dataToProcess.inning.forEach(function(inn,index,arr){
					if(inn.inningNumber == $('#selectInning').val()){
						if($('#selectStatsType option:selected').val() == 'Batsman'){
							inn.battingCard.forEach(function(bc,index,arr1){
								if(bc.status == 'OUT'){
									option = document.createElement('option');
									option.value = bc.player.full_name;
									option.text = bc.player.full_name;
									select.appendChild(option);
								}
							});
						}
						else{
							inn.bowlingCard.forEach(function(boc,index,arr1){
								if(boc.status == 'OTHERBOWLER'){
									option = document.createElement('option');
									option.value = boc.player.full_name;
									option.text = boc.player.full_name;
									select.appendChild(option);
								}
							});
						}	
					}
				});
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
			case'PARTNERSHIP-OPTIONS':
			    option.name = 'animate_in_partnership_btn';
			    option.value = 'Animate In Partnership';
				break;
			case'MATCHSUMMARY-OPTIONS':
			    option.name = 'animate_in_matchsummary_btn';
			    option.value = 'Animate In Matchsummary';
				break;
			case'BUG-OPTIONS':
			    option.name = 'animate_in_bug_btn';
			    option.value = 'Animate In Bug';
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
		    
		    row.insertCell(cellCount).appendChild(div);
		    cellCount = cellCount + 1;
		    
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