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
function initialisePage(whatToProcess)
{
	switch (whatToProcess) {
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
	case 'infobar_graphic_btn': case 'infobar_bottom-right_graphic_btn': case 'playerprofile_graphic_btn': case 'infobar_bottom_graphic_btn': case 'matchid_graphic_btn': case 'fallofwicket_graphic_btn':
	case 'playingxi_graphic_btn': case 'leaderboard_graphic_btn': case 'projected_graphic_btn': case 'target_graphic_btn': case 'teamsummary_graphic_btn': case 'playersummary_graphic_btn':
	case 'l3playerprofile_graphic_btn': case 'comparision_graphic_btn':
		$("#captions_div").hide();
		switch ($(whichInput).attr('name')) {
		case 'scorecard_graphic_btn':
			//alert($('#select_broadcaster').val()); 
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
			processCricketProcedures('BOTTOMLEFT_GRAPHICS-OPTIONS');
			break;
		case 'infobar_graphic_btn':
			processCricketProcedures('INFOBAR_GRAPHICS-OPTIONS');
			break;
		case 'infobar_bottom-right_graphic_btn':
			processCricketProcedures('BOTTOMRIGHT_GRAPHICS-OPTIONS');
			break;
		case 'infobar_bottom_graphic_btn':
			processCricketProcedures('BOTTOM_GRAPHICS-OPTIONS');
			break;
		case 'matchid_graphic_btn':
			addItemsToList('MATCHID-OPTIONS',null);
			break;
		case 'playingxi_graphic_btn':
			processCricketProcedures('ANIMATE_PLAYINGXI-OPTIONS');
			break;
		case 'leaderboard_graphic_btn':
			addItemsToList('LEADERBOARD-OPTIONS',null);
			break;
		case 'projected_graphic_btn':
			processCricketProcedures('PROJECTED_GRAPHICS-OPTIONS');
			break;
		case 'target_graphic_btn':
			processCricketProcedures('TARGET_GRAPHICS-OPTIONS');
			break;
		case 'teamsummary_graphic_btn':
			addItemsToList('TEAMSUMMARY-OPTIONS',null);
			break;
		case 'playersummary_graphic_btn':
			processCricketProcedures('PLAYERSUMMARY_GRAPHICS-OPTIONS');
			//addItemsToList('PLAYERSUMMARY-OPTIONS',null);
			break;
		case 'l3playerprofile_graphic_btn':
			processCricketProcedures('L3PLAYERPROFILE_GRAPHICS-OPTIONS');
			break;
		case 'fallofwicket_graphic_btn':
			addItemsToList('FOW-OPTIONS',null);
			break;
		case 'comparision_graphic_btn':
			processCricketProcedures('COMPARISION-GRAPHICS-OPTIONS');
			break;
		}
		break;
	case 'populate_scorecard_btn': case 'populate_bowlingcard_btn': case 'populate_partnership_btn': case 'populate_matchsummary_btn': case 'populate_bug_btn': case 'populate_howout_btn':
	case 'populate_playerstats_btn': case 'populate_namesuper_btn': case 'populate_playerprofile_btn':  case 'populate_doubleteams_btn': case 'populate_infobar_bottom-left_btn': 
	case 'populate_infobar_btn': case 'populate_infobar_bottom-right_btn': case 'populate_infobar_bottom_btn': case 'populate_matchid_btn': case 'populate_playingxi_btn': case 'populate_leaderboard_btn':
	case 'populate_projected_btn': case 'populate_target_btn': case 'populate_teamsummary_btn': case 'populate_playersummary_btn': case 'populate_l3playerprofile_btn': case 'populate_fow_btn':
	case 'populate_comparision_btn':
		processWaitingButtonSpinner('START_WAIT_TIMER');
		switch ($(whichInput).attr('name')) {
		case 'populate_scorecard_btn':
			processCricketProcedures('POPULATE-FF-SCORECARD');
			break;
		case 'populate_bowlingcard_btn':
			processCricketProcedures('POPULATE-FF-BOWLINGCARD');
			break;
		case 'populate_partnership_btn':
			processCricketProcedures('POPULATE-FF-PARTNERSHIP');
			break;
		case 'populate_matchsummary_btn':
			processCricketProcedures('POPULATE-FF-MATCHSUMMARY');
			break;
		case 'populate_bug_btn':
			processCricketProcedures('POPULATE-L3-BUG');
			break;
		case 'populate_howout_btn':
			
			processCricketProcedures('POPULATE-L3-HOWOUT');
			break;
		case 'populate_playerstats_btn':
			processCricketProcedures('POPULATE-L3-PLAYERSTATS');
			break;
		case 'populate_namesuper_btn':
			processCricketProcedures('POPULATE-L3-NAMESUPER');
			break;
		case 'populate_playerprofile_btn':
			processCricketProcedures('POPULATE-FF-PLAYERPROFILE');
			break;
		case 'populate_doubleteams_btn':
			processCricketProcedures('POPULATE-FF-DOUBLETEAMS');
			break;
		case 'populate_infobar_bottom-left_btn':
			processCricketProcedures('POPULATE-INFOBAR-BOTTOMLEFT');
			break;
		case 'populate_infobar_btn':
			processCricketProcedures('POPULATE-L3-INFOBAR');
			break;
		case 'populate_infobar_bottom-right_btn':
			processCricketProcedures('POPULATE-INFOBAR-BOTTOMRIGHT');
			break;
		case 'populate_infobar_bottom_btn':
			processCricketProcedures('POPULATE-INFOBAR-BOTTOM');
			break;
		case 'populate_matchid_btn':
			processCricketProcedures('POPULATE-FF-MATCHID');
			break;
		case 'populate_playingxi_btn':
			processCricketProcedures('POPULATE-FF-PLAYINGXI');
			break;
		case 'populate_leaderboard_btn':
			processCricketProcedures('POPULATE-FF-LEADERBOARD');
			break;
		case 'populate_projected_btn':
			processCricketProcedures('POPULATE-L3-PROJECTED');
			break;
		case 'populate_target_btn':
			processCricketProcedures('POPULATE-L3-TARGET');
			break;
		case 'populate_teamsummary_btn':
			processCricketProcedures('POPULATE-L3-TEAMSUMMARY');
			break;
		case 'populate_playersummary_btn':
			processCricketProcedures('POPULATE-L3-PLAYERSUMMARY');
			break;
		case 'populate_l3playerprofile_btn':
			processCricketProcedures('POPULATE-L3-PLAYERPROFILE');
			break;
		case 'populate_fow_btn':
			processCricketProcedures('POPULATE-L3-FALLOFWICKET');
			break;
		case 'populate_comparision_btn':
			processCricketProcedures('POPULATE-L3-COMPARISION');
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
	case 'selectTeams':
		switch ($(whichInput).attr('name')) {
		case 'selectTeams':
			addItemsToList('POPULATE-PROFILE',match_data);
			break;
	}
	break;
	case 'selectl3Teams':
		switch ($(whichInput).attr('name')) {
		case 'selectl3Teams':
			addItemsToList('POPULATE-L3PROFILE',match_data);
			break;
	}
	break;
	case 'selectedInning':
		switch ($(whichInput).attr('name')) {
		case 'selectedInning':
			addItemsToList('POPULATE-PLAYERS_DATA',match_data);
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
	case 'POPULATE-FF-SCORECARD': 
		switch ($('#selected_broadcaster').val().toUpperCase()) {
		case 'DOAD_IN_HOUSE_EVEREST':
			valueToProcess = $('#battingScene').val() + ',' + $('#selectInning option:selected').val() ;
			break;	
		}
		break;
	case 'POPULATE-FF-BOWLINGCARD': 
		switch ($('#selected_broadcaster').val().toUpperCase()) {
		case 'DOAD_IN_HOUSE_EVEREST':
			valueToProcess = $('#bowlingScene').val() + ',' + $('#selectInning option:selected').val() ;
			break;	
		}
		break;
	case 'POPULATE-FF-PARTNERSHIP':
		switch ($('#selected_broadcaster').val().toUpperCase()) {
		case 'DOAD_IN_HOUSE_EVEREST':
			valueToProcess = $('#partnershipScene').val() + ',' + $('#selectInning option:selected').val() ;
			break;	
		}
		break;
	case 'POPULATE-FF-MATCHSUMMARY': 
		switch ($('#selected_broadcaster').val().toUpperCase()) {
		case 'DOAD_IN_HOUSE_EVEREST':
			valueToProcess = $('#summaryScene').val() + ',' + $('#selectInning option:selected').val() ;
			break;	
		}
		break;
	case 'POPULATE-L3-BUG':
		switch ($('#selected_broadcaster').val().toUpperCase()) {
		case 'DOAD_IN_HOUSE_EVEREST':
			valueToProcess = $('#bugScene').val() + ',' + $('#selectInning option:selected').val() + ',' + $('#selectStatsType option:selected').val() + ',' + $('#selectPlayers option:selected').val() ;
			break;
		}
		break;	
	case 'POPULATE-L3-HOWOUT':
		switch ($('#selected_broadcaster').val().toUpperCase()) {
		case 'DOAD_IN_HOUSE_EVEREST':
			valueToProcess = $('#howoutScene').val() + ',' + $('#selectInning option:selected').val() + ',' + $('#selectStatsType option:selected').val() + ',' + $('#selectPlayers option:selected').val() ;
			break;
		}
		break;
	case 'POPULATE-L3-PLAYERSTATS':
		switch ($('#selected_broadcaster').val().toUpperCase()) {
		case 'DOAD_IN_HOUSE_EVEREST':
			valueToProcess = $('#playerstatsScene').val() + ',' + $('#selectInning option:selected').val() + ',' + $('#selectStatsType option:selected').val() + ',' + $('#selectPlayers option:selected').val() ;
			break;
		}
		break;
	case 'POPULATE-L3-NAMESUPER':
		switch ($('#selected_broadcaster').val().toUpperCase()) {
		case 'DOAD_IN_HOUSE_EVEREST':
			valueToProcess = $('#namesuperScene').val() + ',' + $('#selectTeam option:selected').val() + ',' + $('#selectCaptainWicketKeeper option:selected').val() + ',' + $('#selectPlayer option:selected').val() ;
			break;
		}
		break;
	case 'POPULATE-FF-PLAYERPROFILE':
		switch ($('#selected_broadcaster').val().toUpperCase()) {
		case 'DOAD_IN_HOUSE_EVEREST':
			valueToProcess = $('#playerprofileScene').val() + ',' + $('#selectPlayerName option:selected').val()+ ',' + $('#selectProfile option:selected').val() + ',' + $('#selectTypeOfProfile option:selected').val() ;
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
	case 'POPULATE-L3-INFOBAR':
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
	case 'POPULATE-INFOBAR-BOTTOM':
		switch ($('#selected_broadcaster').val().toUpperCase()) {
		case 'DOAD_IN_HOUSE_EVEREST':
			valueToProcess = $('#selectBottomStat option:selected').val() ;
			break;
		}
		break;
	case 'POPULATE-FF-MATCHID':
		switch ($('#selected_broadcaster').val().toUpperCase()) {
		case 'DOAD_IN_HOUSE_EVEREST':
			valueToProcess = $('#matchidScene').val();
			break;
		}
		break;
	case 'POPULATE-FF-PLAYINGXI': 
		switch ($('#selected_broadcaster').val().toUpperCase()) {
		case 'DOAD_IN_HOUSE_EVEREST':
			valueToProcess = $('#playingxiScene').val() + ',' + $('#selectPlayingXI option:selected').val() ;
			break;	
		}
		break;
	case 'POPULATE-FF-LEADERBOARD': 
		switch ($('#selected_broadcaster').val().toUpperCase()) {
		case 'DOAD_IN_HOUSE_EVEREST':
			valueToProcess = $('#leaderboardScene').val() + ',' + $('#selectPlayer1').val() + ',' + $('#selectPlayer2').val();
			break;	
		}
		break;
	case 'POPULATE-L3-PROJECTED': 
		switch ($('#selected_broadcaster').val().toUpperCase()) {
		case 'DOAD_IN_HOUSE_EVEREST':
			valueToProcess = $('#projectedScene').val();
			break;	
		}
		break;
	case 'POPULATE-L3-TARGET': 
		switch ($('#selected_broadcaster').val().toUpperCase()) {
		case 'DOAD_IN_HOUSE_EVEREST':
			valueToProcess = $('#targetScene').val();
			break;	
		}
		break;
	case 'POPULATE-L3-TEAMSUMMARY': 
		switch ($('#selected_broadcaster').val().toUpperCase()) {
		case 'DOAD_IN_HOUSE_EVEREST':
			valueToProcess = $('#teamsummaryScene').val() + ',' + $('#selectInning option:selected').val();
			break;	
		}
		break;
	case 'POPULATE-L3-PLAYERSUMMARY': 
		switch ($('#selected_broadcaster').val().toUpperCase()) {
		case 'DOAD_IN_HOUSE_EVEREST':
			valueToProcess = $('#playersummaryScene').val() + ',' + $('#selectedInning option:selected').val() + ',' + $('#selectPlayerData option:selected').val();
			break;	
		}
		break;
	case 'POPULATE-L3-PLAYERPROFILE':
		switch ($('#selected_broadcaster').val().toUpperCase()) {
		case 'DOAD_IN_HOUSE_EVEREST':
			valueToProcess = $('#l3playerprofileScene').val() + ',' + $('#selectl3PlayerName option:selected').val()+ ',' + $('#selectl3Profile option:selected').val() + ',' + $('#selectl3TypeOfProfile option:selected').val() ;
			break;
		}
		break;
	case 'POPULATE-L3-FALLOFWICKET':
		switch ($('#selected_broadcaster').val().toUpperCase()) {
		case 'DOAD_IN_HOUSE_EVEREST':
			valueToProcess = $('#fowScene').val() + ',' + $('#selectInning option:selected').val() ;
			break;
		}
		break;
	case 'POPULATE-FF-DOUBLETEAMS':
		switch ($('#selected_broadcaster').val().toUpperCase()) {
		case 'DOAD_IN_HOUSE_EVEREST':
			valueToProcess = $('#doubleScene').val();
			break;
		}
		break;
	case 'POPULATE-L3-COMPARISION': 
		switch ($('#selected_broadcaster').val().toUpperCase()) {
		case 'DOAD_IN_HOUSE_EVEREST':
			valueToProcess = $('#comparisionScene').val();
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
				if(data){
					if($('#matchFileTimeStamp').val() != data.matchFileTimeStamp) {
						document.getElementById('matchFileTimeStamp').value = data.matchFileTimeStamp;
					}
				}
				break;
			case 'BUG_GRAPHICS-OPTIONS':
				addItemsToList('BUG-OPTIONS',data);
				addItemsToList('POPULATE-PLAYERS',data);
				match_data = data;
				break;
			case 'HOWOUT_GRAPHICS-OPTIONS':
				addItemsToList('HOWOUT-OPTIONS',data);
				addItemsToList('POPULATE-HOWOUT-PLAYERS',data);
				match_data = data;
				break;
			case 'PLAYERSTATS_GRAPHICS-OPTIONS':
				addItemsToList('PLAYERSTATS-OPTIONS',data);
				addItemsToList('POPULATE-PLAYERS',data);
				match_data = data;
				break;
			case 'PLAYERSUMMARY_GRAPHICS-OPTIONS':
				addItemsToList('PLAYERSUMMARY-OPTIONS',data);
				addItemsToList('POPULATE-PLAYERS_DATA',data);
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
			case 'BOTTOMLEFT_GRAPHICS-OPTIONS':
				addItemsToList('INFOBAR-BOTTOMLEFT-OPTIONS',data);
				match_data = data;
				break;
			case 'BOTTOMRIGHT_GRAPHICS-OPTIONS':
				addItemsToList('INFOBAR-BOTTOMRIGHT-OPTIONS',data);
				match_data = data;
				break;
			case 'INFOBAR_GRAPHICS-OPTIONS':
				addItemsToList('INFOBAR-OPTIONS',data);
				match_data = data;
				break;
			case 'BOTTOM_GRAPHICS-OPTIONS':
				addItemsToList('INFOBAR-BOTTOM-OPTIONS',data);
				match_data = data;
				break;
			case 'ANIMATE_PLAYINGXI-OPTIONS':
				addItemsToList('PLAYINGXI-OPTIONS',data);
				match_data = data;
				break;
			case 'PROJECTED_GRAPHICS-OPTIONS':
				addItemsToList('POPULATE-PROJECTED',data);
				match_data = data;
				break;
			case 'TARGET_GRAPHICS-OPTIONS':
				addItemsToList('POPULATE-TARGET',data);
				match_data = data;
				break;
			case 'L3PLAYERPROFILE_GRAPHICS-OPTIONS':
				addItemsToList('L3PLAYERPROFILE-OPTIONS',data);
				addItemsToList('POPULATE-L3PROFILE',data);
				match_data = data;
				break;
			case 'COMPARISION-GRAPHICS-OPTIONS':
				addItemsToList('POPULATE-COMPARISION',data);
				match_data = data;
				break;
			
			case 'POPULATE-FF-SCORECARD': case 'POPULATE-FF-BOWLINGCARD': case 'POPULATE-FF-PARTNERSHIP': case 'POPULATE-FF-MATCHSUMMARY': case 'POPULATE-L3-BUG':  case 'POPULATE-L3-HOWOUT':
			case 'POPULATE-L3-PLAYERSTATS': case 'POPULATE-L3-NAMESUPER': case 'POPULATE-FF-PLAYERPROFILE': case 'POPULATE-FF-DOUBLETEAMS': case 'POPULATE-L3-INFOBAR': case 'POPULATE-FF-MATCHID':
			case 'POPULATE-FF-PLAYINGXI': case 'POPULATE-FF-LEADERBOARD': case 'POPULATE-L3-PROJECTED': case 'POPULATE-L3-TARGET': case 'POPULATE-L3-TEAMSUMMARY': case 'POPULATE-L3-PLAYERSUMMARY':
			case 'POPULATE-L3-PLAYERPROFILE': case 'POPULATE-L3-FALLOFWICKET': case 'POPULATE-L3-COMPARISION':
				if (data.status.toUpperCase() == 'SUCCESSFUL') {
					if(confirm('Animate In?') == true){
						     
						$('#select_graphic_options_div').empty();
						document.getElementById('select_graphic_options_div').style.display = 'none';
						$("#captions_div").show();
						
			        	switch(whatToProcess) {
						case 'POPULATE-FF-SCORECARD': 
							processCricketProcedures('ANIMATE-IN-SCORECARD');
							break;
						case 'POPULATE-FF-BOWLINGCARD':
							processCricketProcedures('ANIMATE-IN-BOWLINGCARD');					
							break;
						case 'POPULATE-FF-PARTNERSHIP':
							processCricketProcedures('ANIMATE-IN-PARTNERSHIP');					
							break;
						case 'POPULATE-FF-MATCHSUMMARY':
							processCricketProcedures('ANIMATE-IN-MATCHSUMARRY');			
							break;
						case 'POPULATE-L3-BUG':
							processCricketProcedures('ANIMATE-IN-BUG');				
							break;
						case 'POPULATE-L3-HOWOUT':
							processCricketProcedures('ANIMATE-IN-HOWOUT');				
							break;
						case 'POPULATE-L3-PLAYERSTATS':
							processCricketProcedures('ANIMATE-IN-PLAYERSTATS');				
							break;
						case 'POPULATE-L3-NAMESUPER':
							processCricketProcedures('ANIMATE-IN-NAMESUPER');				
							break;
						case 'POPULATE-FF-PLAYERPROFILE':
							processCricketProcedures('ANIMATE-IN-PLAYERPROFILE');				
							break;
						case 'POPULATE-FF-DOUBLETEAMS':
							processCricketProcedures('ANIMATE-IN-DOUBLETEAMS');				
							break;
						case 'POPULATE-L3-INFOBAR':
							processCricketProcedures('ANIMATE-IN-INFOBAR');				
							break;
						case 'POPULATE-FF-MATCHID':
							processCricketProcedures('ANIMATE-IN-MATCHID');				
							break;
						case 'POPULATE-FF-PLAYINGXI':
							processCricketProcedures('ANIMATE-IN-PLAYINGXI');					
							break;
						case 'POPULATE-FF-LEADERBOARD':
							processCricketProcedures('ANIMATE-IN-LEADERBOARD');					
							break;
						case 'POPULATE-L3-PROJECTED':
							processCricketProcedures('ANIMATE-IN-PROJECTED');					
							break;
						case 'POPULATE-L3-TARGET':
							processCricketProcedures('ANIMATE-IN-TARGET');					
							break;
						case 'POPULATE-L3-TEAMSUMMARY':
							processCricketProcedures('ANIMATE-IN-TEAMSUMMARY');					
							break;
						case 'POPULATE-L3-PLAYERSUMMARY':
							processCricketProcedures('ANIMATE-IN-PLAYERSUMMARY');					
							break;
						case 'POPULATE-L3-PLAYERPROFILE':
							processCricketProcedures('ANIMATE-IN-L3PLAYERPROFILE');				
							break;
						case 'POPULATE-L3-FALLOFWICKET':
							processCricketProcedures('ANIMATE-IN-FALLOFWICKET');				
							break;
						case 'POPULATE-L3-COMPARISION':
							processCricketProcedures('ANIMATE-IN-COMPARISION');				
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
					$('#playerstatsScene').append(
							$(document.createElement('input')).prop({
			                value: 'D:/DOAD_In_House_Everest/Everest_Cricket/EVEREST_APL2022/Scenes/LT_Batsman_Score.sum',
			                text: 'hello'
			            }))
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
	
	case 'POPULATE-HOWOUT-PLAYERS' :
	
		$('#selectPlayers').empty();

		dataToProcess.inning.forEach(function(inn,index,arr){
			if(inn.inningNumber == $('#selectInning option:selected').val()){
				if($('#selectStatsType option:selected').val() == 'Batsman'){
					inn.battingCard.forEach(function(bc,bc_index,bc_arr){
						if(bc.status != 'NOT OUT' && bc.status != 'STILLTOBAT'){
							$('#selectPlayers').append(
							$(document.createElement('option')).prop({
			                value: bc.playerId,
			                text: bc.player.full_name
			            	}))
			            }					
					});
				}
			     else{
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
	case 'POPULATE-PLAYERS_DATA' :
	
		$('#selectPlayerData').empty();
		dataToProcess.inning.forEach(function(inn,index,arr){
			if(inn.inningNumber == $('#selectedInning option:selected').val()){
				inn.battingCard.forEach(function(bc,bc_index,bc_arr){
		            $('#selectPlayerData').append(
						$(document.createElement('option')).prop({
		                value: bc.playerId,
		                text: bc.player.full_name
		            }))						
				});
			}
		});
		
		break;
	case 'POPULATE-PROFILE' :

		$('#selectPlayerName').empty();
		
		if(dataToProcess.homeTeamId == $('#selectTeams option:selected').val()){
			dataToProcess.homeSquad.forEach(function(hs,index,arr){
				$('#selectPlayerName').append(
					$(document.createElement('option')).prop({
					value: hs.playerId,
					text: hs.full_name
				}))
			});
			dataToProcess.homeOtherSquad.forEach(function(hos,index,arr){
				$('#selectPlayerName').append(
					$(document.createElement('option')).prop({
					value: hos.playerId,
					text: hos.full_name + ' (OTHER)'
				}))
			});
		}
		else{
			dataToProcess.awaySquad.forEach(function(as,index,arr){
				$('#selectPlayerName').append(
					$(document.createElement('option')).prop({
					value: as.playerId,
					text: as.full_name
				}))
			});
			dataToProcess.awayOtherSquad.forEach(function(aos,index,arr){
				$('#selectPlayerName').append(
					$(document.createElement('option')).prop({
					value: aos.playerId,
					text: aos.full_name + ' (OTHER)'
				}))
			});
		}
		break;
	
	case 'POPULATE-L3PROFILE' :

		$('#selectl3PlayerName').empty();
		
		if(dataToProcess.homeTeamId == $('#selectl3Teams option:selected').val()){
			dataToProcess.homeSquad.forEach(function(hs,index,arr){
				$('#selectl3PlayerName').append(
					$(document.createElement('option')).prop({
					value: hs.playerId,
					text: hs.full_name
				}))
			});
			dataToProcess.homeOtherSquad.forEach(function(hos,index,arr){
				$('#selectl3PlayerName').append(
					$(document.createElement('option')).prop({
					value: hos.playerId,
					text: hos.full_name + ' (OTHER)'
				}))
			});
		}
		else{
			dataToProcess.awaySquad.forEach(function(as,index,arr){
				$('#selectl3PlayerName').append(
					$(document.createElement('option')).prop({
					value: as.playerId,
					text: as.full_name
				}))
			});
			dataToProcess.awayOtherSquad.forEach(function(aos,index,arr){
				$('#selectl3PlayerName').append(
					$(document.createElement('option')).prop({
					value: aos.playerId,
					text: aos.full_name + ' (OTHER)'
				}))
			});
		}
		break;
	
	case 'POPULATE-TARGET':
		dataToProcess.inning.forEach(function(t_inn,index,arr1){
			if(t_inn.inningNumber == 1 && t_inn.isCurrentInning == 'YES'){
				if(confirm('TARGET Work Only When Inning is 2 ...\r\n \r\n It may get Affect Your Scene') == true){
					$('#select_graphic_options_div').empty();
					document.getElementById('select_graphic_options_div').style.display = 'none';
					$("#captions_div").show();
				}
				else{
					$('#select_graphic_options_div').empty();
					document.getElementById('select_graphic_options_div').style.display = 'none';
					$("#captions_div").show();
				}
			}
			else if(t_inn.inningNumber == 2 && t_inn.isCurrentInning == 'YES'){
				addItemsToList('TARGET-OPTIONS',null);
			}
			});
		break;
	case 'POPULATE-COMPARISION':
		dataToProcess.inning.forEach(function(inn,index,arr1){
			if(inn.inningNumber == 1 && inn.isCurrentInning == 'YES'){
				if(confirm('COMPARISION Work Only When Inning is 2 ...\r\n \r\n It may get Affect Your Scene') == true){
					$('#select_graphic_options_div').empty();
					document.getElementById('select_graphic_options_div').style.display = 'none';
					$("#captions_div").show();
				}
				else{
					$('#select_graphic_options_div').empty();
					document.getElementById('select_graphic_options_div').style.display = 'none';
					$("#captions_div").show();
				}
			}
			else if(inn.inningNumber == 2 && inn.isCurrentInning == 'YES'){
				addItemsToList('COMPARISION-OPTIONS',null);
			}
			});
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
	case 'POPULATE-PROJECTED':
		dataToProcess.inning.forEach(function(inn,index,arr2){
			if(inn.inningNumber == 2 && inn.isCurrentInning == 'YES'){
				if(confirm('Projected Score Work Only When Inning is 1 ...\r\n \r\n It may get Affect Your Scene') == true){
					$('#select_graphic_options_div').empty();
					document.getElementById('select_graphic_options_div').style.display = 'none';
					$("#captions_div").show();
				}
				else{
					$('#select_graphic_options_div').empty();
					document.getElementById('select_graphic_options_div').style.display = 'none';
					$("#captions_div").show();
				}
				//alert('Projected Score Work Only When Inning is 1 ...');
			}
			else{
				addItemsToList('PROJECTED-OPTIONS',null);
			}
			});
		break;
	
 	
	case 'SCORECARD-OPTIONS': case'BOWLINGCARD-OPTIONS': case'PARTNERSHIP-OPTIONS': case'MATCHSUMMARY-OPTIONS': case'BUG-OPTIONS': case'HOWOUT-OPTIONS': 
	case'PLAYERSTATS-OPTIONS': case'TEAMSUMMARY-OPTIONS': case'FOW-OPTIONS':
	
	
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
				select.value = 'D:/DOAD_In_House_Everest/Everest_Scenes/APL2022/Scenes/MI_BUG.sum';
				
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
				select.value = 'D:/DOAD_In_House_Everest/Everest_Cricket/EVEREST_APL2022/Scenes/LT_Batsman_Out.sum';
				
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
				select.value = 'D:/DOAD_In_House_Everest/Everest_Cricket/EVEREST_APL2022/Scenes/LT_Batsman_Score.sum';
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
				select.value = 'D:/DOAD_In_House_Everest/Everest_Cricket/EVEREST_APL2022/Scenes/Batting_Card.sum';
				
				row.insertCell(cellCount).appendChild(select);
				cellCount = cellCount + 1;
				 
			    option.name = 'populate_scorecard_btn';
			    option.value = 'Populate Scorecard';
				break;
			case'BOWLINGCARD-OPTIONS':
				select = document.createElement('input');
				select.type = "text";
				select.id = 'bowlingScene';
				select.value = 'D:/DOAD_In_House_Everest/Everest_Cricket/EVEREST_APL2022/Scenes/BowlingCard.sum';
				
				row.insertCell(cellCount).appendChild(select);
				cellCount = cellCount + 1;
				
			    option.name = 'populate_bowlingcard_btn';
			    option.value = 'Populate Bowlingcard';
				break;
			case'PARTNERSHIP-OPTIONS':
				select = document.createElement('input');
				select.type = "text";
				select.id = 'partnershipScene';
				select.value = 'D:/DOAD_In_House_Everest/Everest_Scenes/APL2022/Scenes/MI_Bowling_Card.sum';
				
				row.insertCell(cellCount).appendChild(select);
				cellCount = cellCount + 1;
				
			    option.name = 'populate_partnership_btn';
			    option.value = 'Populate Partnership';
				break;
			case'MATCHSUMMARY-OPTIONS':
				select = document.createElement('input');
				select.type = "text";
				select.id = 'summaryScene';
				select.value = 'D:/DOAD_In_House_Everest/Everest_Scenes/APL2022/Scenes/MI_Summary.sum';
				
				row.insertCell(cellCount).appendChild(select);
				cellCount = cellCount + 1;
				
			    option.name = 'populate_matchsummary_btn';
			    option.value = 'Populate Matchsummary';
				break;
			case'TEAMSUMMARY-OPTIONS':
				select = document.createElement('input');
				select.type = "text";
				select.id = 'teamsummaryScene';
				select.value = 'D:/DOAD_In_House_Everest/Everest_Cricket/EVEREST_APL2022/Scenes/LT_BattingSummary.sum';
				
				row.insertCell(cellCount).appendChild(select);
				cellCount = cellCount + 1;
				
			    option.name = 'populate_teamsummary_btn';
			    option.value = 'Populate Team Summary';
				break;
			case'FOW-OPTIONS':
				select = document.createElement('input');
				select.type = "text";
				select.id = 'fowScene';
				select.value = 'D:/DOAD_In_House_Everest/Everest_Cricket/EVEREST_APL2022/Scenes/LT_FOW.sum';
				
				row.insertCell(cellCount).appendChild(select);
				cellCount = cellCount + 1;
				
			    option.name = 'populate_fow_btn';
			    option.value = 'Populate FallofWicket';
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
		
	case'NAMESUPER-OPTIONS': case'PLAYERPROFILE-OPTIONS': case'L3PLAYERPROFILE-OPTIONS':
	
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
				select.value = 'D:/DOAD_In_House_Everest/Everest_Scenes/APL2022/Scenes/MI_LT_NameSuper.sum';
				
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
				select.id = 'selectPlayerName';
				select.name = select.id;
				
				select.setAttribute('onchange',"processUserSelection(this)");
				row.insertCell(cellCount).appendChild(select);
				cellCount = cellCount + 1;
				
				select = document.createElement('select');
				select.id = 'selectProfile';
				select.name = select.id;
				
				option = document.createElement('option');
				option.value = 'DT20';
				option.text = 'DT20';
				select.appendChild(option);
				
				option = document.createElement('option');
				option.value = 'ODI';
				option.text = 'ODI';
				select.appendChild(option);
				
				option = document.createElement('option');
				option.value = 'Test';
				option.text = 'Test';
				select.appendChild(option);
				
				option = document.createElement('option');
				option.value = 'IT20';
				option.text = 'IT20';
				select.appendChild(option);
				
				select.setAttribute('onchange',"processUserSelection(this)");
				
				row.insertCell(cellCount).appendChild(select);
				cellCount = cellCount + 1;
				
				select = document.createElement('select');
				select.id = 'selectTypeOfProfile';
				select.name = select.id;
				
				option = document.createElement('option');
				option.value = 'Batsman';
				option.text = 'Batsman';
				select.appendChild(option);
				
				option = document.createElement('option');
				option.value = 'Bowler';
				option.text = 'Bowler';
				select.appendChild(option);
				
				row.insertCell(cellCount).appendChild(select);
				cellCount = cellCount + 1;
				
				select = document.createElement('input');
				select.type = "text";
				select.id = 'playerprofileScene';
				select.value = 'D:/DOAD_In_House_Everest/Everest_Scenes/APL2022/Scenes/MI_PlayerProfile.sum';
				
				row.insertCell(cellCount).appendChild(select);
				cellCount = cellCount + 1;
				
				break;
				
			case'L3PLAYERPROFILE-OPTIONS':
				select = document.createElement('select');
				select.id = 'selectl3Teams';
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
				select.id = 'selectl3PlayerName';
				select.name = select.id;
				
				select.setAttribute('onchange',"processUserSelection(this)");
				row.insertCell(cellCount).appendChild(select);
				cellCount = cellCount + 1;
				
				select = document.createElement('select');
				select.id = 'selectl3Profile';
				select.name = select.id;
				
				option = document.createElement('option');
				option.value = 'DT20';
				option.text = 'DT20';
				select.appendChild(option);
				
				option = document.createElement('option');
				option.value = 'ODI';
				option.text = 'ODI';
				select.appendChild(option);
				
				option = document.createElement('option');
				option.value = 'Test';
				option.text = 'Test';
				select.appendChild(option);
				
				option = document.createElement('option');
				option.value = 'IT20';
				option.text = 'IT20';
				select.appendChild(option);
				
				select.setAttribute('onchange',"processUserSelection(this)");
				
				row.insertCell(cellCount).appendChild(select);
				cellCount = cellCount + 1;
				
				select = document.createElement('select');
				select.id = 'selectl3TypeOfProfile';
				select.name = select.id;
				
				option = document.createElement('option');
				option.value = 'Batsman';
				option.text = 'Batsman';
				select.appendChild(option);
				
				option = document.createElement('option');
				option.value = 'Bowler';
				option.text = 'Bowler';
				select.appendChild(option);
				
				row.insertCell(cellCount).appendChild(select);
				cellCount = cellCount + 1;
				
				select = document.createElement('input');
				select.type = "text";
				select.id = 'l3playerprofileScene';
				select.value = 'D:/DOAD_In_House_Everest/Everest_Cricket/EVEREST_APL2022/Scenes/LT_PlayerProfile.sum';
				
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
			case'L3PLAYERPROFILE-OPTIONS':
			    option.name = 'populate_l3playerprofile_btn';
			    option.value = 'Populate L3Playerprofile';
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
							if(inn.fallsOfWickets.length > 0){ 
								select = document.createElement('select');
								select.id = 'selectBottomRightStat';
								select.name = select.id;
								
								option = document.createElement('option');
								option.value = 'toss_winning';
								option.text = 'Toss Winning';
								select.appendChild(option);
								
								option = document.createElement('option');
								option.value = 'last_wicket';
								option.text = 'Last Wicket';
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
								
								row.insertCell(cellCount).appendChild(select);
								cellCount = cellCount + 1;
							}
						}
						else{
							if(inn.fallsOfWickets.length > 0){
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
								option.value = 'last_wicket';
								option.text = 'Last Wicket';
								select.appendChild(option);
								
								option = document.createElement('option');
								option.value = 'comparision';
								option.text = 'Comparision';
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
								option.value = 'comparision';
								option.text = 'Comparision';
								select.appendChild(option);
								
								row.insertCell(cellCount).appendChild(select);
								cellCount = cellCount + 1;
							}
							
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
	case'INFOBAR-BOTTOM-OPTIONS':
	
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
			case'INFOBAR-BOTTOM-OPTIONS':
				dataToProcess.inning.forEach(function(inn,index,arr){
					if(inn.isCurrentInning == 'YES'){
						if(inn.inningNumber == 1){
							select = document.createElement('select');
							select.id = 'selectBottomStat';
							select.name = select.id;
							
							option = document.createElement('option');
							option.value = 'boundaries';
							option.text = 'Boundaries';
							select.appendChild(option);
							
							option = document.createElement('option');
							option.value = 'partnership';
							option.text = 'Partnership';
							select.appendChild(option);
							
							option = document.createElement('option');
							option.value = 'projected_score';
							option.text = 'Projected_Score';
							select.appendChild(option);
							
							row.insertCell(cellCount).appendChild(select);
							cellCount = cellCount + 1;
						}
						else{
							select = document.createElement('select');
							select.id = 'selectBottomStat';
							select.name = select.id;
							
							option = document.createElement('option');
							option.value = 'boundaries';
							option.text = 'Boundaries';
							select.appendChild(option);
							
							option = document.createElement('option');
							option.value = 'partnership';
							option.text = 'Partnership';
							select.appendChild(option);
							
							/*option = document.createElement('option');
							option.value = 'timeline';
							option.text = 'Timeline';
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
			case'INFOBAR-BOTTOM-OPTIONS':
			    option.name = 'populate_infobar_bottom_btn';
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
			select.value = 'D:/DOAD_In_House_Everest/Everest_Cricket/EVEREST_APL2022/Scenes/Scorebug.sum';
			
			
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
		
	case'PLAYINGXI-OPTIONS':
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
			select.id = 'selectPlayingXI';
			select.name = select.id;
			
			
			option = document.createElement('option');
			option.value = dataToProcess.homeTeamId;
			option.text = dataToProcess.homeTeam.shortname;
			select.appendChild(option);
			
			option = document.createElement('option');
			option.value = dataToProcess.awayTeamId;
			option.text = dataToProcess.awayTeam.shortname;
			select.appendChild(option);
			
			row.insertCell(cellCount).appendChild(select);
			cellCount = cellCount + 1;
			
			select = document.createElement('input');
			select.type = "text";
			select.id = 'playingxiScene';
			select.value = 'D:/DOAD_In_House_Everest/Everest_Cricket/EVEREST_APL2022/Scenes/Playing_XI_Image.sum';
			
			row.insertCell(cellCount).appendChild(select);
			cellCount = cellCount + 1;
			
			option = document.createElement('input');
		    option.type = 'button';
		    option.name = 'populate_playingxi_btn';
		    option.value = 'Populate PlayingXI';
		    
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
	case'MATCHID-OPTIONS':
	
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
			select.id = 'matchidScene';
			select.value = 'D:/DOAD_In_House_Everest/Everest_Cricket/EVEREST_APL2022/Scenes/Match_ID.sum';
			
			row.insertCell(cellCount).appendChild(select);
			cellCount = cellCount + 1;
			
			option = document.createElement('input');
		    option.type = 'button';
		   	option.name = 'populate_matchid_btn';
		    option.value = 'Populate MatchID';
				
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
	
	case 'PROJECTED-OPTIONS':
	
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
			select.id = 'projectedScene';
			select.value = 'D:/DOAD_In_House_Everest/Everest_Cricket/EVEREST_APL2022/Scenes/LT_Projected_Score.sum';
			
			row.insertCell(cellCount).appendChild(select);
			cellCount = cellCount + 1;
			
			option = document.createElement('input');
		    option.type = 'button';
		   	option.name = 'populate_projected_btn';
		    option.value = 'Populate Projected';
				
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
	
	case 'TARGET-OPTIONS':
	
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
			select.id = 'targetScene';
			select.value = 'D:/DOAD_In_House_Everest/Everest_Cricket/EVEREST_APL2022/Scenes/LT_Target.sum';
			
			row.insertCell(cellCount).appendChild(select);
			cellCount = cellCount + 1;
			
			option = document.createElement('input');
		    option.type = 'button';
		   	option.name = 'populate_target_btn';
		    option.value = 'Populate Target';
				
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
		    
			select = document.createElement('input');
			select.type = "text";
			select.id = 'doubleScene';
			select.value = 'D:/DOAD_In_House_Everest/Everest_Cricket/EVEREST_APL2022/Scenes/Playing_XI_Both.sum';
			
			row.insertCell(cellCount).appendChild(select);
			cellCount = cellCount + 1;
			
			option = document.createElement('input');
		    option.type = 'button';
		   	option.name = 'populate_doubleteams_btn';
		    option.value = 'Populate DoubleTeams';
				
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
	case'PLAYERSUMMARY-OPTIONS':
	
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
			select.id = 'selectedInning';
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
			
			case'PLAYERSUMMARY-OPTIONS':
			
				select.setAttribute('onchange',"processUserSelection(this)");
		
				select = document.createElement('select');
				select.id = 'selectPlayerData';
				select.name = select.id;
				
				row.insertCell(cellCount).appendChild(select);
				cellCount = cellCount + 1;
				
				select = document.createElement('input');
				select.type = "text";
				select.id = 'playersummaryScene';
				select.value = 'D:/DOAD_In_House_Everest/Everest_Cricket/EVEREST_APL2022/Scenes/LT_BattingSummary.sum';
				
				row.insertCell(cellCount).appendChild(select);
				cellCount = cellCount + 1;
				
				option = document.createElement('input');
		    	option.type = 'button';
		    	
		    	option.name = 'populate_playersummary_btn';
			    option.value = 'Populate Player Summary';
			    
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
		break;
	case 'COMPARISION-OPTIONS':
	
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
			select.id = 'comparisionScene';
			select.value = 'D:/DOAD_In_House_Everest/Everest_Cricket/EVEREST_APL2022/Scenes/LT_Comparison.sum';
			
			row.insertCell(cellCount).appendChild(select);
			cellCount = cellCount + 1;
			
			option = document.createElement('input');
		    option.type = 'button';
		   	option.name = 'populate_comparision_btn';
		    option.value = 'Populate Comparision';
				
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