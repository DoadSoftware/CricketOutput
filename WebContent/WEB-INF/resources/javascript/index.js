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
	case 'bug_animate_in_btn':
		if ($('#selectPlayer:selected').val()) {
			processCricketProcedures('POPULATE-ANIMATE-BUG');
		} else {
			alert('No player selected');
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
	case 'POPULATE-ANIMATE-BUG':
		valueToProcess = 'Stats:' +  $('#selectStats').find(":selected") + 
			'|Inning:' + $('#selectInning').find(":selected").val() + 
			'|Player:' + $('#selectPlayer').find(":selected").val();
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
			case 'POPULATE-ANIMATE-BUG':
				break;
			case 'POPULATE-SELECT-PLAYER': case 'READ-MATCH-AND-POPULATE':
				addItemsToList(whatToProcess,data);
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
	case 'READ-MATCH-AND-POPULATE':
		
		if(dataToProcess){
			$('#fruit_captions_div').empty();
		}
		if(dataToProcess) {
			dataToProcess.inning.forEach(function(inn,index,arr) {
				if(inn.isCurrentInning.toUpperCase() == 'YES') {
					
					/*if(inn.battingTeamId == match.homeTeamId){
						header_text = document.createElement('h6')
						header_text.innnHTML = match.homeTeam;
						document.getElementById('fruit_captions_div').appendChild(header_text);
					}
					else if(inn.battingTeamId == match.awayTeamId)
					{
						header_text = document.createElement('h6')
						header_text.innnHTML = match.awayTeam;
						document.getElementById('fruit_captions_div').appendChild(header_text);
					}*/
						
					header_text = document.createElement('h6');
					header_text.innerHTML = 'Total ' + inn.totalRuns+'-'+inn.totalWickets+'('+inn.totalOvers+'.'+inn.totalBalls+')';
					document.getElementById('fruit_captions_div').appendChild(header_text);
					
					header_text = document.createElement('h6');
					header_text.innerHTML = 'Run Rate-' +  (inn.totalRuns/inn.totalOvers);
					document.getElementById('fruit_captions_div').appendChild(header_text);
					
					header_text = document.createElement('h6');
					header_text.innerHTML = 'Extras-' + inn.totalExtras+' '+'('+inn.totalWides+'wd, '+inn.totalByes+'b, '+inn.totalLegByes+'lb, '+inn.totalNoBalls+'nb'+')';
					document.getElementById('fruit_captions_div').appendChild(header_text);
					
					
					
					/*header_text = document.createElement('h6');
					header_text.innerHTML = 'This Over-' +  inn.totalBalls;
					document.getElementById('fruit_captions_div').appendChild(header_text);*/
					
				}
			});
			$('#match_file_timestamp').attr('value',dataToProcess.match_file_timestamp);
		}
		break;
		
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
		
	case 'POPULATE-SELECT-PLAYER':
		
		$('#select_batsman').empty();
		select = document.getElementById('selectPlayer');
		
		dataToProcess.inning.forEach(function(inn,index,arr) {
			if(inn.inningNumber == $('#selectInning:selected').val()) {
				switch ($('#selectStats:selected').val()) {
				case 'batsmanStats':
				    inn.battingCard.forEach(function(bc,bc_index,bc_arr){
						if(bc.runs > 0 || bc.balls > 0) {
							option = document.createElement('option');
							option.value = bc.playerId;
						    option.text = bc.player.surname + ': ' + bc.runs + ' (' + bc.balls + ')';
						    if(bc.onStrike.toLowerCase() == 'yes') {
							    option.selected = true;
						    }
						    select.appendChild(option);
						}
					});
					break;
				}
			}
		});
		break;
		
	case 'BUG-OPTIONS':

		$('#select_graphic_options_div').empty();

		header_text = document.createElement('h6');
		header_text.innerHTML = 'Select Bug Options';
		document.getElementById('select_graphic_options_div').appendChild(header_text);
		
		table = document.createElement('table');
		table.setAttribute('class', 'table table-bordered');
				
		tbody = document.createElement('tbody');

		table.appendChild(tbody);
		document.getElementById('select_graphic_options_div').appendChild(table);
		
		row = tbody.insertRow(tbody.rows.length);
		
		max_cols = 3;
		
		for(var i=1; i<=max_cols; i++) {
			select = document.createElement('select');
			select.setAttribute('onchange',"processCricketProcedures('POPULATE-SELECT-PLAYER',this)");				
			row.insertCell(i-1).appendChild(select);
			switch (i) {
			case 1: // Stats type
				select.id = 'selectStats';
				for(var j=1; j<=3; j++) {
					option = document.createElement('option');
					switch (j) {
					case 1:
						option.value = 'batsmanStats';
					    option.text = "Batsman's Stats";
						break;
					case 2:
						option.value = 'bowlerStats';
					    option.text = "Bowler's Stats";
						break;
					case 3:
						option.value = 'teamStats';
					    option.text = "Team's Stats";
						break; 
					}
				    select.appendChild(option);
				}
				break;
			case 2: // Select inning
				select.id = 'selectInning';
				select.setAttribute('onchange',"processCricketProcedures('POPULATE-SELECT-PLAYER',this)");				
				dataToProcess.inning.forEach(function(inn,index,arr){
					option = document.createElement('option');
					option.value = inn.inningNumber;
				    option.text = 'Inning ' + inn.inningNumber;
					if(inn.isCurrentInning.toLowerCase() == 'yes') {
					    option.selected = true;
					}
				    select.appendChild(option);
				});
				break;
			case 3: // Select player
				select.id = 'selectPlayer';
			}
		}

	    option = document.createElement('input');
	    option.type = 'button';
	    option.name = 'bug_animate_in_btn';
	    option.value = 'Animate In Bug';
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

	    div.append(document.createElement('br'));
	    div.append(option);
	    
	    row.insertCell(max_cols).appendChild(div);
	    
		document.getElementById('select_graphic_options_div').style.display = '';
		
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
/*function uploadFormDataToSessionObjects(whatToProcess)
{
	var formData = new FormData();
	var url_path;

	$('input, select, textarea').each(
		function(index){  
			formData.append($(this).attr('id'),document.getElementById($(this).attr('id')).value);  
		}
	);
	
	switch(whatToProcess.toUpperCase()) {
	case 'POPULATE-ANIMATE-BUG':
		url_path = 'populate_animate_in_bug';
		break;
	}
	
	$.ajax({    
		headers: {'X-CSRF-TOKEN': $('meta[name="_csrf"]').attr('content')},
        url : url_path,     
        data : formData,
        cache: false,
        contentType: false,
        processData: false,
        type: 'POST',     
        success : function(response) {
        	switch(whatToProcess.toUpperCase()) {
        	case 'POPULATE-ANIMATE-BUG':
        		break;
        	}
        },    
        error : function(e) {    
       	 	console.log('Error occured in uploadFormDataToSessionObjects with error description = ' + e);     
        }    
    });		
	
} */