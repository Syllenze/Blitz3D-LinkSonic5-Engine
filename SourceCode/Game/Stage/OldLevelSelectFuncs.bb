; Old Level Select Funcs

Function StageSelect1()
;	Game\Gameplay\TypingStage = 1
	While Not KeyHit(1); Or Game\Gameplay\TypingStage = 0
		Cls
		If (Game\Gameplay\TypingStage = 1) Then
			GUI_UpdateGUI()
			GUI_UpdateEdit(winMain)
			GUI_UpdateButton(winMain)
			
			If GUI_AppEvent() = ButtonExit Then End
			
			If GUI_AppEvent() = ButtonGo Then
				yn$ = GUI_Message(Edt1,"GetText")
				
			EndIf
			
		;Else
			;GUI_FreeGUI()
			
		EndIf
		Flip
		
		If yn$ <> "" Then
			
			HidePointer()
			PlaySound Sound_SelectLevel
			Game\Path$ = "Stages/" + Yn$ + "/"
			Game\State	= GAME_STATE_START
			Game\Gameplay\TypingStage = 0
			GUI_FreeGUI
			
		EndIf
		
		
	Wend
;GUI_FreeGUI()
	End
	
End Function

Function StageSelect()
	
	Cls
	;	StartDraw()
	;	DrawImageEx(Interface_MenuBG, 32*GAME_WINDOW_SCALE#, 32*GAME_WINDOW_SCALE#)
		;STAGE SELECT;
	
	Locate 100,200
	Print info1$:Print info2$:Print info3$:Print info4$:Print
	Print( "Type the name of the stage folder, or type 'quit' to exit: " )
	If (Game\Gameplay\TypingStage = 1) Then
		Game\Stage\Yn$=Input$("")
	EndIf
	
	
	If Right$( Lower$( Game\Stage\Yn$ ),4 )="quit" Then
		End
		
	EndIf
	
	If Right$( Lower$( Game\Stage\Yn$ ),6 )="online" Then
		NewGame = StartNetGame()
	;	Path$ = "Stages/GreenHill/"
		Game\Online = 1
		Game\Stage\Yn$ = "GreenHill"
		If NewGame = 1 Then
	;	Game_Startup()
			PlayerID = CreateNetPlayer("Player")
			Game\Player2 = 1
			Game\Player1 = 0
		EndIf
		If NewGame = 2 Then
	;	Game_Startup()
			PlayerID = CreateNetPlayer("Server")
			Game\Player1 = 1
			Game\Player2 = 0
		EndIf
	EndIf
	
		; MISC
	
	If Right$( Lower$( Game\Stage\Yn$ ),15 )="source code plz" Then
		RuntimeError("NEVAAAAR")
	EndIf
	
	If Right$( Lower$( Game\Stage\Yn$ ),8 )="fuck you" Then
		RuntimeError("That's not very nice! D:")
	EndIf
	
	If Right$( Lower$( Game\Stage\Yn$ ),10 )="i love you" Then
		RuntimeError("<3")
	EndIf
	
	If Right$( Lower$( Game\Stage\Yn$ ),10 )="i hate you" Then
		RuntimeError("</3")
	EndIf
	
	If Right$( Lower$( Game\Stage\Yn$ ),16 )="do a barrel roll" Then
		RuntimeError("I can't let you do that, StarFox")
	EndIf
	
	If Right$( Lower$( Game\Stage\Yn$ ),12 )="keyboard cat" Then
		RuntimeError("No. Nyan cat.")
	EndIf
	
	If Right$( Lower$( Game\Stage\Yn$ ),15 )="this game sucks" Then
		RuntimeError("Let's see you make a better one!")
	EndIf
	
	If Right$( Lower$( Game\Stage\Yn$ ),11 )="i am hungry" Then
		RuntimeError("Well get some food you lazy kid! XD")
	EndIf
	
	If Right$( Lower$( Game\Stage\Yn$ ),3 )="hoy" Then
		RuntimeError("NO! That's MY word!! D:<")
	EndIf
	
	If Right$( Lower$( Game\Stage\Yn$ ),12 )="safe landing" Then
		RuntimeError("GRIFFAGES!")
	EndIf
	
	If Right$( Lower$( Game\Stage\Yn$ ),19 )="what is your email?" Then
		RuntimeError("You can contact me at LinkSonic5@live.com")
	EndIf
	
	If Right$( Lower$( Game\Stage\Yn$ ),16 )="linksonic5 rules" Then
		RuntimeError("You're too kind :')")
	EndIf
	
	If Right$( Lower$( Game\Stage\Yn$ ),16 )="blitzsonic sucks" Then
		RuntimeError("We've all been down that road...")
	EndIf
	
	If Right$( Lower$( Game\Stage\Yn$ ),9 )="athbucket" Then
		RuntimeError("He will be missed. Truly a hero, and a friend to all of us.")
	EndIf
	
	If Right$( Lower$( Game\Stage\Yn$ ),16 )="what time is it?" Then
		RuntimeError("Time to get a watch")
	EndIf
	
	If Right$( Lower$( Game\Stage\Yn$ ),12 )="th33mediainc" Then
		RuntimeError("He'll PAWNCH your nipple counter-clockwise!")
	EndIf
	
	If Right$( Lower$( Game\Stage\Yn$ ),16 )="killingwithpants" Then
		RuntimeError("Shut your monkey.")
	EndIf
	
	If Right$( Lower$( Game\Stage\Yn$ ),6 )="medic!" Then
		RuntimeError("Nein.")
	EndIf
	
	If Right$( Lower$( Game\Stage\Yn$ ),26 )="stout shako for 2 refined" Then
		RuntimeError("Fuck That Shit")
	EndIf
	
	If Right$( Lower$( Game\Stage\Yn$ ),23 )="this statement is false" Then
		RuntimeError("True. I'll go true.")
	EndIf
	
		; MISC
	
	HidePointer()
	PlaySound Sound_SelectLevel
	Game\Path$ = "Stages/" + Game\Stage\Yn$ + "/"
	Game\State	= GAME_STATE_START
	Game\Gameplay\TypingStage = 0
	Game\Stage\Yn$ = ""
	
		;STAGE SELECT;
	;	EndDraw()
End Function
;~IDEal Editor Parameters:
;~C#Blitz3D