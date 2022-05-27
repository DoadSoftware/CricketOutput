var match_data,vizScene;
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
	case 'scorecard_graphic_btn': case 'bowlingcard_graphic_btn': case 'partnership_graphic_btn': case 'matchsummary_graphic_btn':  case 'howout_graphic_btn':
	case 'playerstats_graphic_btn': case 'namesuper_graphic_btn': case 'playerprofile_graphic_btn': case 'doubleteams_graphic_btn': case 'infobar_bottom-left_graphic_btn': 
	case 'infobar_graphic_btn': case 'infobar_bottom-right_graphic_btn':
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
			processCricketProcedures('BUG_GRAPHICS-OPTIONS');
			break;
		case 'howout_graphic_btn':
			processCricketProcedures('HOWOUT_GRAPHICS-OPTIONS');
			break;
		case 'playerstats_graphic_btn':
			processCricketProcedures('PLAYERSTATS_GRAPHICS-OPTIONS');
			break;
		case 'namesuper_graphic_btn':
			processCricketProcedures('NAMESUPER_GRAPHICS-OPTIONS');
			break;
		case 'playerprofile_graphic_btn':
			processCricketProcedures('PLAYERPROFILE_GRAPHICS-OPTIONS');
			break;
		case 'doubleteams_graphic_btn':
			addItemsToList('DOUBLETEAMS-OPTIONS',null);
			break;
		case 'infobar_bottom-left_graphic_btn':
			processCricketProcedures('ANIMATE-OPTIONS');
			//processCricketProcedures('ANIMATE-BOTTOMLEFT-OPTIONS');
			break;
		case 'infobar_graphic_btn':
			processCricketProcedures('INFOBAR_GRAPHICS-OPTIONS');
			//processCricketProcedures('INFOBAR-OPTIONS');
			break;
		case 'infobar_bottom-right_graphic_btn':
			processCricketProcedures('ANIMATE_GRAPHICS-OPTIONS');
			//processCricketProcedures('ANIMATE-BOTTOMLEFT-OPTIONS');
			break;
		}
		break;
	case 'populate_scorecard_btn': case 'populate_bowlingcard_btn': case 'populate_partnership_btn': case 'populate_matchsummary_btn': case 'populate_bug_btn': case 'populate_howout_btn':
	case 'populate_playerstats_btn': case 'populate_namesuper_btn': case 'populate_playerprofile_btn':  case 'populate_doubleteams_btn': case 'populate_infobar_bottom-left_btn': 
	case 'populate_infobar_btn': case 'populate_infobar_bottom-right_btn':
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
		case 'populate_playerstats_btn':
			processCricketProcedures('POPULATE-PLAYERSTATS');
			break;
		case 'populate_namesuper_btn':
			processCricketProcedures('POPULATE-NAMESUPER');
			break;
		case 'populate_playerprofile_btn':
			processCricketProcedures('POPULATE-PLAYERPROFILE');
			break;
		case 'populate_doubleteams_btn':
			processCricketProcedures('POPULATE-DOUBLETEAMS');
			break;
		case 'populate_infobar_bottom-left_btn':
			processCricketProcedures('POPULATE-INFOBAR-BOTTOMLEFT');
			break;
		case 'populate_infobar_btn':
			processCricketProcedures('POPULATE-INFOBAR');
			break;
		case 'populate_infobar_bottom-right_btn':
			processCricketProcedures('POPULATE-INFOBAR-BOTTOMRIGHT');
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
		case 'DOAD_IN_HOUSE_VIZ':
			$('#vizPortNumber').attr('value','6100');
			$('#vizScene').attr('value','/Default/DOAD_In_House/BatBallSummary');
			//$('#vizScene').attr('value','/Default/DOAD_In_House/Bugs');
			//$('#vizScene').attr('value','/Default/DOAD_In_House/Lt_HowOut');
			//$('#vizScene').attr('value','/Default/DOAD_In_House/TeamLineUpSingles');
			break;
			
		case 'DOAD_IN_HOUSE_EVEREST':
			
			$('#vizPortNumber').attr('value','1980');
			$('label[for=vizScene], input#vizScene').hide();
			break;
		}
		break;
	case 'load_scene_btn':
		if(checkEmpty($('#vizIPAddress'),'IP Address Blank') == false
			|| checkEmpty($('#vizPortNumber'),'Port Number Blank') == false) {
			return false;
		}
      	document.initialise_form.submit();
		break;
	case 'selectInning': case 'selectStatsType':
		switch ($(whichInput).attr('name')) {
		case 'selectInning':
			addItemsToList('POPULATE-PLAYERS',match_data);
			break;
		case 'selectStatsType':
			addItemsToList('POPULATE-PLAYERS',match_data);
			break;
		}
		break;
	case 'selectTeam': case 'selectCaptianWicketKeeper':
		switch ($(whichInput).attr('name')) {
		case 'selectTeam':
			addItemsToList('POPULATE-PLAYER',match_data);
			break;
		case 'selectCaptianWicketKeeper':
			addItemsToList('POPULATE-PLAYER',match_data);
			break;
		}
		break;
	case 'PlayerType': case 'selectTeams':
		switch ($(whichInput).attr('name')) {
		case 'selectTeams':
			addItemsToList('POPULATE-PROFILE',match_data);
			break;
		case 'PlayerType':
			addItemsToList('POPULATE-PROFILE',match_data);
			break;
		}
		break;
	}
}
function processCricketProcedures(whatToProcess)
{
	var valueToProcess;
	switch(whatToProcess) {
	case 'READ-MATCH-AND-POPULATE':
		valueToProcess = $('#matchFileTimeStamp').val();
		break;
	case 'POPULATE-SCORECARD': 
		switch ($('#selected_broadcaster').val().toUpperCase()) {
		case 'DOAD_IN_HOUSE_EVEREST':
			
			//valueToProcess = $('#bowlingScene option:selected').val();
			valueToProcess = $('#battingScene').val() + ',' + $('#selectInning option:selected').val() ;
			//alert("Scene =" + $('#bowlingScene').val());
			//alert("valueToProcess = " + valueToProcess);
			break;	
		}
		break;
	case 'POPULATE-BOWLINGCARD': 
		switch ($('#selected_broadcaster').val().toUpperCase()) {
		case 'DOAD_IN_HOUSE_EVEREST':
			
			//valueToProcess = $('#bowlingScene option:selected').val();
			valueToProcess = $('#bowlingScene').val() + ',' + $('#selectInning option:selected').val() ;
			//alert("Scene =" + $('#bowlingScene').val());
			//alert("valueToProcess = " + valueToProcess);
			break;	
		}
		break;
	case 'POPULATE-PARTNERSHIP':
		switch ($('#selected_broadcaster').val().toUpperCase()) {
		case 'DOAD_IN_HOUSE_EVEREST':
			
			//valueToProcess = $('#bowlingScene option:selected').val();
			valueToProcess = $('#partnershipScene').val() + ',' + $('#selectInning option:selected').val() ;
			//alert("Scene =" + $('#bowlingScene').val());
			//alert("valueToProcess = " + valueToProcess);
			break;	
		}
		break;
	case 'POPULATE-MATCHSUMMARY': 
		switch ($('#selected_broadcaster').val().toUpperCase()) {
		case 'DOAD_IN_HOUSE_EVEREST':
			
			//valueToProcess = $('#bowlingScene option:selected').val();
			valueToProcess = $('#summaryScene').val() + ',' + $('#selectInning option:selected').val() ;
			//alert("Scene =" + $('#bowlingScene').val());
			//alert("valueToProcess = " + valueToProcess);
			break;	
		}
		break;
	case 'POPULATE-BUG':
		switch ($('#selected_broadcaster').val().toUpperCase()) {
		case 'DOAD_IN_HOUSE_EVEREST':
			valueToProcess = $('#bugScene').val() + ',' + $('#selectInning option:selected').val() + ',' + $('#selectStatsType option:selected').val() + ',' + $('#selectPlayers option:selected').val() ;
			break;
		}
		break;	
	case 'POPULATE-HOWOUT':
		switch ($('#selected_broadcaster').val().toUpperCase()) {
		case 'DOAD_IN_HOUSE_EVEREST':
			valueToProcess = $('#howoutScene').val() + ',' + $('#selectInning option:selected').val() + ',' + $('#selectStatsType option:selected').val() + ',' + $('#selectPlayers option:selected').val() ;
			break;
		}
		break;
	case 'POPULATE-PLAYERSTATS':
		switch ($('#selected_broadcaster').val().toUpperCase()) {
		case 'DOAD_IN_HOUSE_EVEREST':
			valueToProcess = $('#playerstatsScene').val() + ',' + $('#selectInning option:selected').val() + ',' + $('#selectStatsType option:selected').val() + ',' + $('#selectPlayers option:selected').val() ;
			break;
		}
		break;
	case 'POPULATE-NAMESUPER':
		switch ($('#selected_broadcaster').val().toUpperCase()) {
		case 'DOAD_IN_HOUSE_EVEREST':
			valueToProcess = $('#namesuperScene').val() + ',' + $('#selectTeam option:selected').val() + ',' + $('#selectCaptainWicketKeeper option:selected').val() + ',' + $('#selectPlayer option:selected').val() ;
			break;
		}
		break;
	case 'POPULATE-PLAYERPROFILE':
		switch ($('#selected_broadcaster').val().toUpperCase()) {
		case 'DOAD_IN_HOUSE_EVEREST':
			valueToProcess = $('#playerprofileScene').val() + ',' + $('#selectTeam option:selected').val() + ',' + $('#PlayerType option:selected').val() + ',' + $('#selectPlayer option:selected').val() ;
			break;
		}
		break;
	case 'POPULATE-INFOBAR-BOTTOMLEFT':
		switch ($('#selected_broadcaster').val().toUpperCase()) {
		case 'DOAD_IN_HOUSE_EVEREST':
			valueToProcess = $('#selectBottomLeftStat option:selected').val() ;
			break;
		}
		break;
	case 'POPULATE-INFOBAR':
		switch ($('#selected_broadcaster').val().toUpperCase()) {
		case 'DOAD_IN_HOUSE_EVEREST':
			valueToProcess = $('#infobarScene').val() + ',' + $('#selectTopLeftStats option:selected').val() + ',' + $('#selectTopRightStats option:selected').val() + ',' + $('#selectBottomLeftStats option:selected').val() + ',' + $('#selectBottomRightStats option:selected').val() ;
			
			break;
		}
		break;
	case 'POPULATE-INFOBAR-BOTTOMRIGHT':
		switch ($('#selected_broadcaster').val().toUpperCase()) {
		case 'DOAD_IN_HOUSE_EVEREST':
			valueToProcess = $('#selectBottomRightStat option:selected').val() ;
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
			case 'BUG_GRAPHICS-OPTIONS':
				addItemsToList('BUG-OPTIONS',data);
				addItemsToList('POPULATE-PLAYERS',data);
				match_data = data;
				break;
			case 'HOWOUT_GRAPHICS-OPTIONS':
				addItemsToList('HOWOUT-OPTIONS',data);
				addItemsToList('POPULATE-PLAYERS',data);
				match_data = data;
				break;
			case 'PLAYERSTATS_GRAPHICS-OPTIONS':
				addItemsToList('PLAYERSTATS-OPTIONS',data);
				addItemsToList('POPULATE-PLAYERS',data);
				match_data = data;
				break;
			case 'NAMESUPER_GRAPHICS-OPTIONS':
				addItemsToList('NAMESUPER-OPTIONS',data);
				addItemsToList('POPULATE-PLAYER',data);
				match_data = data;
				break;
			case 'PLAYERPROFILE_GRAPHICS-OPTIONS':
				addItemsToList('PLAYERPROFILE-OPTIONS',data);
				addItemsToList('POPULATE-PROFILE',data);
				match_data = data;
				break;
			case 'ANIMATE-OPTIONS':
				addItemsToList('INFOBAR-BOTTOMLEFT-OPTIONS',data);
				match_data = data;
				break;
			case 'ANIMATE_GRAPHICS-OPTIONS':
				addItemsToList('INFOBAR-BOTTOMRIGHT-OPTIONS',data);
				match_data = data;
				break;
			case 'INFOBAR_GRAPHICS-OPTIONS':
				addItemsToList('INFOBAR-OPTIONS',data);
				match_data = data;
				break;
			
			case 'POPULATE-SCORECARD': case 'POPULATE-BOWLINGCARD': case 'POPULATE-PARTNERSHIP': case 'POPULATE-MATCHSUMMARY': case 'POPULATE-BUG':  case 'POPULATE-HOWOUT':
			case 'POPULATE-PLAYERSTATS': case 'POPULATE-NAMESUPER': case 'POPULATE-PLAYERPROFILE': case 'POPULATE-DOUBLETEAMS': case 'POPULATE-INFOBAR':
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
						case 'POPULATE-PLAYERSTATS':
							processCricketProcedures('ANIMATE-IN-PLAYERSTATS');				
							break;
						case 'POPULATE-NAMESUPER':
							processCricketProcedures('ANIMATE-IN-NAMESUPER');				
							break;
						case 'POPULATE-PLAYERPROFILE':
							processCricketProcedures('ANIMATE-IN-PLAYERPROFILE');				
							break;
						case 'POPULATE-DOUBLETEAMS':
							processCricketProcedures('ANIMATE-IN-DOUBLETEAMS');				
							break;
						case 'POPULATE-INFOBAR':
							processCricketProcedures('ANIMATE-IN-INFOBAR');				
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
	case 'POPULATE-PLAYERS' :
	
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
		
		break;
	case 'POPULATE-PROFILE' :
	
		$('#selectProfile').empty();

			if(dataToProcess.homeTeamId ==  $('#selectTeams option:selected').val()){
				dataToProcess.homeSquad.forEach(function(hs,index,arr){
					if(hs.role == $('#PlayerType option:selected').val()){
						$('#selectProfile').append(
							$(document.createElement('option')).prop({
			                value: hs.playerId,
			                text: hs.full_name
				        }))	
					}				
				});
			}
			else{
				dataToProcess.awaySquad.forEach(function(as,index,arr){
					if(as.role == $('#PlayerType option:selected').val()){
						$('#selectProfile').append(
							$(document.createElement('option')).prop({
			                value: as.playerId,
			                text: as.full_name
				        }))	
					}				
				});
			}
			
		break;
	case 'POPULATE-PLAYER' :
		$('#selectPlayer').empty();
		
		if(dataToProcess.homeTeamId ==  $('#selectTeam option:selected').val()){
			dataToProcess.homeSquad.forEach(function(hs,index,arr){
				$('#selectPlayer').append(
					$(document.createElement('option')).prop({
	                value: hs.playerId,
	                text: hs.full_name
		        }))					
			});
		}
		else{
			dataToProcess.awaySquad.forEach(function(as,index,arr){
				$('#selectPlayer').append(
					$(document.createElement('option')).prop({
	                value: as.playerId,
	                text: as.full_name
		        }))					
			});
		}
		
		break;
 	
	case 'SCORECARD-OPTIONS': case'BOWLINGCARD-OPTIONS': case'PARTNERSHIP-OPTIONS': case'MATCHSUMMARY-OPTIONS': case'BUG-OPTIONS': case'HOWOUT-OPTIONS': 
	case'PLAYERSTATS-OPTIONS':
	
	
		switch ($('#selected_broadcaster').val().toUpperCase()) {
		case 'DOAD_IN_HOUSE_EVEREST':

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
				
				select = document.createElement('input');
				select.type = "text";
				select.id = 'bugScene';
				select.value = 'C:/Everest_Scenes/Mumbai_Indians/Final/Layers/MI_BUG.sum';
				
				row.insertCell(cellCount).appendChild(select);
				cellCount = cellCount + 1;
				
				break;
				
			case'HOWOUT-OPTIONS': 
			
				select.setAttribute('onchange',"processUserSelection(this)");
				
				select = document.createElement('select');
				select.id = 'selectStatsType';
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
				
				select = document.createElement('input');
				select.type = "text";
				select.id = 'howoutScene';
				select.value = 'C:/Everest_Scenes/Mumbai_Indians/Final/Layers/MI_LT_Batsman_Out.sum';
				
				row.insertCell(cellCount).appendChild(select);
				cellCount = cellCount + 1;
				
				break;
			case'PLAYERSTATS-OPTIONS':
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
				
				select = document.createElement('input');
				select.type = "text";
				select.id = 'playerstatsScene';
				select.value = 'C:/Everest_Scenes/Mumbai_Indians/Final/Layers/MI_LT_Batsman_Out.sum';
				
				row.insertCell(cellCount).appendChild(select);
				cellCount = cellCount + 1;
				
				break;
				
			}
			
			option = document.createElement('input');
		    option.type = 'button';
		    
			switch (whatToProcess) {
			case 'SCORECARD-OPTIONS':
				select = document.createElement('input');
				select.type = "text";
				select.id = 'battingScene';
				select.value = 'C:/Everest_Scenes/Mumbai_Indians/Final/Layers/MI_Batting_Card.sum';
				
				row.insertCell(cellCount).appendChild(select);
				cellCount = cellCount + 1;
				 
			    option.name = 'populate_scorecard_btn';
			    option.value = 'Populate Scorecard';
				break;
			case'BOWLINGCARD-OPTIONS':
				select = document.createElement('input');
				select.type = "text";
				select.id = 'bowlingScene';
				select.value = 'C:/Everest_Scenes/Mumbai_Indians/Final/Layers/MI_Bowling_Card.sum';
				
				row.insertCell(cellCount).appendChild(select);
				cellCount = cellCount + 1;
				
			    option.name = 'populate_bowlingcard_btn';
			    option.value = 'Populate Bowlingcard';
				break;
			case'PARTNERSHIP-OPTIONS':
				select = document.createElement('input');
				select.type = "text";
				select.id = 'partnershipScene';
				select.value = 'C:/Everest_Scenes/Mumbai_Indians/Final/Layers/MI_Bowling_Card.sum';
				
				row.insertCell(cellCount).appendChild(select);
				cellCount = cellCount + 1;
				
			    option.name = 'populate_partnership_btn';
			    option.value = 'Populate Partnership';
				break;
			case'MATCHSUMMARY-OPTIONS':
				select = document.createElement('input');
				select.type = "text";
				select.id = 'summaryScene';
				select.value = 'C:/Everest_Scenes/Mumbai_Indians/Final/Layers/MI_Summary.sum';
				
				row.insertCell(cellCount).appendChild(select);
				cellCount = cellCount + 1;
				
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
			case'PLAYERSTATS-OPTIONS':
			    option.name = 'populate_playerstats_btn';
			    option.value = 'Populate Playerstats';
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
		
	case'NAMESUPER-OPTIONS': case'PLAYERPROFILE-OPTIONS':
	
		switch ($('#selected_broadcaster').val().toUpperCase()) {
		case 'DOAD_IN_HOUSE_EVEREST':

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
			
			switch(whatToProcess){
				
				case'NAMESUPER-OPTIONS':
				
				select = document.createElement('select');
				select.id = 'selectTeam';
				select.name = select.id;
				
				option = document.createElement('option');
				option.value = dataToProcess.homeTeamId;
				option.text = dataToProcess.homeTeam.shortname;
				select.appendChild(option);
				
				option = document.createElement('option');
				option.value = dataToProcess.awayTeamId;
				option.text = dataToProcess.awayTeam.shortname;
				select.appendChild(option);
			
				select.setAttribute('onchange',"processUserSelection(this)");
				row.insertCell(cellCount).appendChild(select);
				cellCount = cellCount + 1;

				select = document.createElement('select');
				select.id = 'selectCaptainWicketKeeper';
				select.name = select.id;
				
				option = document.createElement('option');
				option.value = 'Captain';
				option.text = 'Captain';
				select.appendChild(option);
				
				option = document.createElement('option');
				option.value = 'Captain-WicketKeeper';
				option.text = 'Captain-WicketKeeper';
				select.appendChild(option);
				
				option = document.createElement('option');
				option.value = 'Player';
				option.text = 'Player';
				select.appendChild(option);

				option = document.createElement('option');
				option.value = 'Player Of The Match';
				option.text = 'Player Of The Match';
				select.appendChild(option);
				
				option = document.createElement('option');
				option.value = 'WicketKeeper';
				option.text = 'WicketKeeper';
				select.appendChild(option);
				
				select.setAttribute('onchange',"processUserSelection(this)");
				row.insertCell(cellCount).appendChild(select);
				
				cellCount = cellCount + 1;
				
				select = document.createElement('select');
				select.id = 'selectPlayer';
				select.name = select.id;
				
				row.insertCell(cellCount).appendChild(select);

				cellCount = cellCount + 1;
				
				select = document.createElement('input');
				select.type = "text";
				select.id = 'namesuperScene';
				select.value = 'C:/Everest_Scenes/Mumbai_Indians/Final/Layers/MI_LT_NameSuper.sum';
				
				row.insertCell(cellCount).appendChild(select);
				cellCount = cellCount + 1;
				
				break;
				
			case'PLAYERPROFILE-OPTIONS':
				
				select = document.createElement('select');
				select.id = 'selectTeams';
				select.name = select.id;
				
				option = document.createElement('option');
				option.value = dataToProcess.homeTeamId;
				option.text = dataToProcess.homeTeam.shortname;
				select.appendChild(option);
				
				option = document.createElement('option');
				option.value = dataToProcess.awayTeamId;
				option.text = dataToProcess.awayTeam.shortname;
				select.appendChild(option);
				
				select.setAttribute('onchange',"processUserSelection(this)");
				row.insertCell(cellCount).appendChild(select);
				cellCount = cellCount + 1;
				
				select = document.createElement('select');
				select.id = 'PlayerType';
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
				select.id = 'selectProfile';
				select.name = select.id;
				
				row.insertCell(cellCount).appendChild(select);

				cellCount = cellCount + 1;
				
				select = document.createElement('input');
				select.type = "text";
				select.id = 'playerprofileScene';
				select.value = 'C:/Everest_Scenes/Mumbai_Indians/Final/Layers/MI_PlayerProfile.sum';
				
				row.insertCell(cellCount).appendChild(select);
				cellCount = cellCount + 1;
				
				break;
			}
			
			option = document.createElement('input');
		    option.type = 'button';
		    
			switch (whatToProcess) {
			
			case'NAMESUPER-OPTIONS':
			    option.name = 'populate_namesuper_btn';
			    option.value = 'Populate Namesuper';
				break;
			case'PLAYERPROFILE-OPTIONS':
			    option.name = 'populate_playerprofile_btn';
			    option.value = 'Populate Playerprofile';
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
		case 'DOAD_IN_HOUSE_EVEREST':

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
				select = document.createElement('input');
				select.type = "text";
				select.id = 'doubleteamScene';
				select.value = 'C:/Everest_Scenes/Mumbai_Indians/Final/Layers/MI_LT_Batsman_Out.sum';
				
				row.insertCell(cellCount).appendChild(select);
				cellCount = cellCount + 1;
				
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
	case'INFOBAR-BOTTOMLEFT-OPTIONS':
	
		switch ($('#selected_broadcaster').val().toUpperCase()) {
		case 'DOAD_IN_HOUSE_EVEREST':

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
			
			switch(whatToProcess){
			case'INFOBAR-BOTTOMLEFT-OPTIONS':
				dataToProcess.inning.forEach(function(inn,index,arr){
					if(inn.isCurrentInning == 'YES'){
						if(inn.inningNumber == 1){
							select = document.createElement('select');
							select.id = 'selectBottomLeftStat';
							select.name = select.id;
							
							option = document.createElement('option');
							option.value = 'current_run_rate';
							option.text = 'Current Run Rate';
							select.appendChild(option);
							
							option = document.createElement('option');
							option.value = 'vs_bowling_team';
							option.text = 'vs Bowling team';
							select.appendChild(option);
							
							row.insertCell(cellCount).appendChild(select);
							cellCount = cellCount + 1;
						}
						else{
							select = document.createElement('select');
							select.id = 'selectBottomLeftStat';
							select.name = select.id;
							
							option = document.createElement('option');
							option.value = 'current_run_rate';
							option.text = 'Current Run Rate';
							select.appendChild(option);
							
							option = document.createElement('option');
							option.value = 'vs_bowling_team';
							option.text = 'vs Bowling team';
							select.appendChild(option);
							
							option = document.createElement('option');
							option.value = 'required_run_rate';
							option.text = 'Required Run Rate';
							select.appendChild(option);
							
							option = document.createElement('option');
							option.value = 'target';
							option.text = 'Target';
							select.appendChild(option);
							
							row.insertCell(cellCount).appendChild(select);
							cellCount = cellCount + 1;
							
						}
					}
				});
				
				break;		
				}

			option = document.createElement('input');
		    option.type = 'button';
		    
			switch (whatToProcess) {
			case'INFOBAR-BOTTOMLEFT-OPTIONS':
			    option.name = 'populate_infobar_bottom-left_btn';
			    option.value = 'Change on Infobar';
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
	case'INFOBAR-BOTTOMRIGHT-OPTIONS':
	
		switch ($('#selected_broadcaster').val().toUpperCase()) {
		case 'DOAD_IN_HOUSE_EVEREST':

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
			
			switch(whatToProcess){
			case'INFOBAR-BOTTOMRIGHT-OPTIONS':
				dataToProcess.inning.forEach(function(inn,index,arr){
					if(inn.isCurrentInning == 'YES'){
						if(inn.inningNumber == 1){
							select = document.createElement('select');
							select.id = 'selectBottomRightStat';
							select.name = select.id;
							
							option = document.createElement('option');
							option.value = 'toss_winning';
							option.text = 'Toss Winning';
							select.appendChild(option);
							
							option = document.createElement('option');
							option.value = 'boundaries';
							option.text = 'Boundaries';
							select.appendChild(option);
							
							option = document.createElement('option');
							option.value = 'partnership';
							option.text = 'Partnership';
							select.appendChild(option);
							
							row.insertCell(cellCount).appendChild(select);
							cellCount = cellCount + 1;
						}
						else{
							select = document.createElement('select');
							select.id = 'selectBottomRightStat';
							select.name = select.id;
							
							option = document.createElement('option');
							option.value = 'toss_winning';
							option.text = 'Toss Winning';
							select.appendChild(option);
							
							option = document.createElement('option');
							option.value = 'equation';
							option.text = 'Equation';
							select.appendChild(option);
							
							option = document.createElement('option');
							option.value = 'boundaries';
							option.text = 'Boundaries';
							select.appendChild(option);
							
							option = document.createElement('option');
							option.value = 'partnership';
							option.text = 'Partnership';
							select.appendChild(option);
							
							/*option = document.createElement('option');
							option.value = 'comparision';
							option.text = 'Comparision';
							select.appendChild(option);*/
							
							row.insertCell(cellCount).appendChild(select);
							cellCount = cellCount + 1;
						}
					}
				});
				
				break;		
				}

			option = document.createElement('input');
		    option.type = 'button';
		    
			switch (whatToProcess) {
			case'INFOBAR-BOTTOMRIGHT-OPTIONS':
			    option.name = 'populate_infobar_bottom-right_btn';
			    option.value = 'Change on Infobar';
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
	case 'INFOBAR-OPTIONS':
		//alert("Broadcaster = " +$('#selected_broadcaster').val());
		switch ($('#selected_broadcaster').val().toUpperCase()) {
		case 'DOAD_IN_HOUSE_EVEREST':
			
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
			
			select = document.createElement('input');
			select.type = "text";
			select.id = 'infobarScene';
			select.value = 'C:/Everest_Scenes/Mumbai_Indians/Final/Layers/MI_Scorebug.sum';
			
			
			row.insertCell(cellCount).appendChild(select);
			cellCount = cellCount + 1;
			
			select = document.createElement('select');
			select.id = 'selectTopLeftStats';
			select.name = select.id;
			
			
			option = document.createElement('option');
			option.value = 'batsman';
			option.text = 'Batsman';
			select.appendChild(option);
			
			
			row.insertCell(cellCount).appendChild(select);
			
			cellCount = cellCount + 1;
			
			select = document.createElement('select');
			select.id = 'selectTopRightStats';
			select.name = select.id;
			
			option = document.createElement('option');
			option.value = 'bowler';
			option.text = 'Bowler';
			select.appendChild(option);
			
			option = document.createElement('option');
			option.value = 'this_over';
			option.text = 'This Over';
			select.appendChild(option);
			
			row.insertCell(cellCount).appendChild(select);
			cellCount = cellCount + 1;
			
			dataToProcess.inning.forEach(function(inn,index,arr){
					if(inn.isCurrentInning == 'YES'){
						if(inn.inningNumber == 1){
							select = document.createElement('select');
							select.id = 'selectBottomLeftStats';
							select.name = select.id;
							
							option = document.createElement('option');
							option.value = 'current_run_rate';
							option.text = 'Current Run Rate';
							select.appendChild(option);
							
							option = document.createElement('option');
							option.value = 'vs_bowling_team';
							option.text = 'vs Bowling Team';
							select.appendChild(option);
							row.insertCell(cellCount).appendChild(select);
							cellCount = cellCount + 1;
							
							select = document.createElement('select');
							select.id = 'selectBottomRightStats';
							select.name = select.id;
							
							option = document.createElement('option');
							option.value = 'toss_winning';
							option.text = 'Toss Winning';
							select.appendChild(option);
							
							option = document.createElement('option');
							option.value = 'boundaries';
							option.text = 'Boundaries';
							select.appendChild(option);
							
							option = document.createElement('option');
							option.value = 'partnership';
							option.text = 'Partnership';
							select.appendChild(option);
							row.insertCell(cellCount).appendChild(select);
							cellCount = cellCount + 1;
						
					}
					else{
						select = document.createElement('select');
						select.id = 'selectBottomLeftStats';
						select.name = select.id;
						
						option = document.createElement('option');
						option.value = 'current_run_rate';
						option.text = 'Current Run Rate';
						select.appendChild(option);
						
						option = document.createElement('option');
						option.value = 'vs_bowling_team';
						option.text = 'vs Bowling Team';
						select.appendChild(option);
						
						option = document.createElement('option');
						option.value = 'required_run_rate';
						option.text = 'Required Run Rate';
						select.appendChild(option);
						
						option = document.createElement('option');
						option.value = 'target';
						option.text = 'Target';
						select.appendChild(option);				
						row.insertCell(cellCount).appendChild(select);
						cellCount = cellCount + 1;
						
						select = document.createElement('select');
						select.id = 'selectBottomRightStats';
						select.name = select.id;
						
						option = document.createElement('option');
						option.value = 'equation';
						option.text = 'Equation';
						select.appendChild(option);
						
						/*option = document.createElement('option');
						option.value = 'comparision';
						option.text = 'Comparision';
						select.appendChild(option);*/
						
						option = document.createElement('option');
						option.value = 'boundaries';
						option.text = 'Boundaries';
						select.appendChild(option);
						
						option = document.createElement('option');
						option.value = 'partnership';
						option.text = 'Partnership';
						select.appendChild(option);
						row.insertCell(cellCount).appendChild(select);
						cellCount = cellCount + 1;
					}
					}
				});
				
			option = document.createElement('input');
		    option.type = 'button';
		    option.name = 'populate_infobar_btn';
			option.value = 'Populate Infobar';
			
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