; ------------------------------------------------------------------------
; BlitzSonic Engine -- Classic Sonic the Hedgehog engine for Blitz 3D
; version 0.1, February 7th, 2008
;
; Copyright (C) 2008 - BlitzSonic Team.
; ------------------------------------------------------------------------
;
; This software is provided 'as-is', without any express or implied
; warranty.  In no event will the authors be held liable for any damages
; arising from the use of this software.
; 
; Permission is granted to anyone to use this software for any purpose
; (except for commercial applications) and to alter it and redistribute
; it freely subject to the following restrictions:
;
; 1. The origin of this software must not be misrepresented; you must not
;    claim that you wrote the original software. If you use this software
;    in a product, an acknowledgment in the product itself as a splash
;    screen is required.
; 2. Altered source versions must be plainly marked as such, and must not be
;    misrepresented as being the original software.
; 3. This notice may not be removed or altered from any source distribution.
;
; All characters and materials in relation to the Sonic the Hedgehog game series
; are copyrights/trademarks of SEGA of Japan (SEGA Co., LTD). This product
; has been developed without permission of SEGA, therefore it's prohibited
; to sell/make profit of it.
;
; The BlitzSonic Team:
; - Hйctor "Damizean" (elgigantedeyeso at gmail dot com)
; - Mark "Corй" (mabc_bh at yahoo dot com dot br)
; - Streak Thunderstorm
; - Mista ED
;

;\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/;
;	Project Title : Sonic the Hedgehog                                                                         ;
; ============================================================================================================ ;
;	Author :                                                                                                   ;
;	Email :                                                                                                    ;
;	Version: 0.1                                                                                               ;
;	Date: --/--/2008                                                                                           ;
;                                                                                                              ;
;\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/;
;                                                                                                              ;
;   Changelog:  -(Damizean)------------------------------->                                                    ;
;               19/01/2008 - Code reorganization.                                                              ;
;                                                                                                              ;
;==============================================================================================================;
;                                                                                                              ;
;   TODO:                                                                                                      ;
;                                                                                                              ;
;==============================================================================================================;

; /\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/
; 	CONSTANTS
; /\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/

	; --- Collisions constants ---
	Const COLLISION_NONE				=	0
	Const COLLISION_PLAYER				=	1
	Const COLLISION_CAMERA				=	2
	
	Const COLLISION_WORLD_POLYGON		=	3
	Const COLLISION_WORLD_POLYGON_ALIGN	=	4
	Const COLLISION_WORLD_BOX			=	5
	
	Const COLLISION_SPEWRING			=	6
	
	Const COLLISION_WORLD_HURT			=	7
	Const COLLISION_WORLD_DIE			=	8
	
	Const COLLISION_NORESPONSE			=	9
	
	Const COLLISION_WORLD_INTERSECTTEST	=	10
	Const COLLISION_WORLD_CAM			=	11
	
	Const COLLISION_SCENE_PLATFORM		=	12
	Const COLLISION_WORLD_RAIL			=	13
	Const COLLISION_GRINDAFFECTOR		=	14
	Const COLLISION_BOXDETECT			=	15
	Const COLLISION_WORLD_LEDGE			=	16
	
	Type MeshStructure
		Field Entity
		Field SoundEntity
		Field CamEntity
		Field WaterEntity
		Field LavaEntity
		Field Rail
	End Type
	
	Global CMWater

; /\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/
; 	METHODS
; /\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/
	
	; ---------------------------------------------------------------------------------------------------------
	; Game_Stage_Update
	; ---------------------------------------------------------------------------------------------------------
	Function Game_Stage_Update()
		Select Game\State
			Case GAME_STATE_SELECT1: StartMenu()
			Case GAME_STATE_SELECT: LevelSelect()
			Case GAME_STATE_START : Game_Stage_Start()
		;	Case GAME_STATE_SETUP : Game_Stage_Setup()
			Case GAME_STATE_STEP  : Game_Stage_Step()
			Case GAME_STATE_END   : Game_Stage_End()
			Case GAME_STATE_END1   : Game_Stage_End1()
		End Select
	End Function
	
	; ---------------------------------------------------------------------------------------------------------
	; STAGE SELECT
	; ---------------------------------------------------------------------------------------------------------
	
;	Global CubeMapTex
	Global raddefchar
	Global betamodebox
	
	Global geted$;the returned string
	
Function LevelSelect()
	
	If (Game\CreatedMenu = 0) Then
		GUI_InitGUI("Skins\BackRow.skin")
		
		Game\Gameplay\TypingStage = 1
		
	;	fileout = WriteFile("Data\Defaults.dat")
		
	;	If (Eof = 1) Then
	;		WriteString(fileout, "Sonic")
	;		WriteString(fileout, "False")
	;		WriteString(fileout, "True")
	;		WriteString(fileout, "Hub")
	;	EndIf
		
	;	CloseFile(fileout)
		
		
		
		filein = ReadFile("Data\Defaults.dat")
		
		Read1$ = ReadLine(filein)
		Read2# = ReadLine(filein)
		Read3# = ReadLine(filein)
		Read4# = ReadLine(filein)
		Read5$ = ReadLine(filein)
		Read6# = ReadLine(filein)
		Read7# = ReadLine(filein)
		Read8# = ReadLine(filein)
		Read9# = ReadLine(filein)
		Read10$ = ReadLine(filein)
		Read11$ = ReadLine(filein)
		Read12# = ReadLine(filein)
		Read13# = ReadLine(filein)
		Read14# = ReadLine(filein)
		Read15# = ReadLine(filein)
		Read16# = ReadLine(filein)
		Read17# = ReadLine(filein)
		Read18$ = ReadLine(filein)
		
		CloseFile(filein)
		
		If (Game\StageError = 1) Then
			GUI_MsgBox("Error", "An error occurred while loading the stage you selected." + Chr(10) + "Try To correct any errors you may have made within the stage folder." + Chr(10) + "The file path you typed was: " + Game\Path$)
			Game\StageError = 0
		EndIf
		
		Win 					= GUI_CreateWindow(30, 100*Game_Window_Scale#, 500, 520, "Menu", "", False)
		WinMenu					= GUI_CreateMenu(Win, "Option")
		
		creditbutton			= GUI_CreateButton(win, 20, 425, 75, 21, "Credits", "", True)
		exitbutton				= GUI_CreateButton(win, 100, 425, 75, 21, "EXIT", "", True)
		
		grp1					= GUI_CreateGroupBox(Win, 10, 10, 200, 300, "Stage Select")
		edit1					= GUI_CreateEdit(grp1, 10, 13, 180, Read5$)
		
		charlabel				= GUI_CreateLabel(grp1, 10, 55, "Character")
		edit2					= GUI_CreateEdit(grp1, 30, 68, 100, Read1$)
		radsonic				= GUI_CreateRadio(grp1, 10, 68, "", 0, 1, Read12#)
		GUI_CreateLabel(grp1, 130, 68, "Sonic")
		
		edit3					= GUI_CreateEdit(grp1, 30, 88, 100, Read10$)
		radtails				= GUI_CreateRadio(grp1, 10, 88, "", 0, 1, Read13#)
		GUI_CreateLabel(grp1, 130, 88, "Tails")
		
		edit4					= GUI_CreateEdit(grp1, 30, 108, 100, Read11$)
		radknuckles				= GUI_CreateRadio(grp1, 10, 108, "", 0, 1, Read14#)
		GUI_CreateLabel(grp1, 130, 108, "Knuckles")
		
		raddefchar				= GUI_CreateRadio(grp1, 10, 128, "Use Default Character In Stage.xml", 0, 1, Read15#)
		
		gobutton				= GUI_CreateButton(grp1, 15, 155, 75, 21, "GO", "", True)
		testbutton				= GUI_CreateButton(grp1, 95, 155, 75, 21, "Test Stage", "Uses 'Test' as a file path", True)
		clearbutton				= GUI_CreateButton(grp1, 10, 35, 55, 21, "Clear", "", True)
		
		joinbutton				= GUI_CreateButton(grp1, 15, 195, 75, 21, "Join Game", "", True)
		hostbutton				= GUI_CreateButton(grp1, 15, 225, 75, 21, "Host Game", "", True)
		editipjoin				= GUI_CreateEdit(grp1, 95, 195, 75, Read17#)
	;	editiphost				= GUI_CreateEdit(grp1, 95, 225, 75, Read18#)
		editname				= GUI_CreateEdit(grp1, 95, 255, 75, Read18$)
		namelabel				= GUI_CreateLabel(grp1, 15, 255, "MP Name")
		
		grp2					= GUI_CreateGroupBox(Win, 220, 10, 230, 270, "Options")
		debugbox				= GUI_CreateCheckBox(grp2, 30, 30, "Debug", True, Read2#)
		htrackbox				= GUI_CreateCheckBox(grp2, 30, 50, "Head Tracking", True, Read3#)
		tiltbox					= GUI_CreateCheckBox(grp2, 30, 110, "Tilting", True, Read7#)
		footstepbox				= GUI_CreateCheckBox(grp2, 30, 130, "Footsteps", True, Read8#)
		fxbox					= GUI_CreateCheckBox(grp2, 30, 150, "FastExtension PostFX", True, Read9#)
		
		htrackhelpbutton		= GUI_CreateButton(grp2, 130, 50, 30, 21, "?", "", True)
		stagehelpbutton			= GUI_CreateButton(grp1, 70, 35, 30, 21, "?", "", True)
	;	htracklabel				= GUI_CreateLabel(htrackbox, 0, 0, "EXPERIMENTAL!", "Left")
		
		bblastbox				= GUI_CreateCheckBox(grp2, 30, 70, "Enable Barrier-Blast", True, Read4#)
		soniccambox				= GUI_CreateCheckBox(grp2, 30, 90, "Enable Loop Cam", True, Read6#)
		betamodebox				= GUI_CreateCheckBox(grp2, 30, 170, "Enable 'Beta Tape Mode'", True, Read16#)
	;	selectbutton			= GUI_MsgBox("Button", "GO")
		
		grp3					= GUI_CreateGroupBox(Win, 10, 320, 470, 100, "Preset Stages")
		
	;	For stg.tGame_StageList = Each tGame_StageList
		
		;Game\Stage\List = First tGame_StagesList
		
		stg1button				= GUI_CreateButton(grp3, 10, 20, 75, 21, Game\Stage\List\Folder1$, "", True)
		stg2button				= GUI_CreateButton(grp3, 90, 20, 75, 21, Game\Stage\List\Folder2$, "", True)
		stg3button				= GUI_CreateButton(grp3, 170, 20, 75, 21, Game\Stage\List\Folder3$, "", True)
		stg4button				= GUI_CreateButton(grp3, 250, 20, 75, 21, Game\Stage\List\Folder4$, "", True)
		stg5button				= GUI_CreateButton(grp3, 330, 20, 75, 21, Game\Stage\List\Folder5$, "", True)
		
		stg6button				= GUI_CreateButton(grp3, 10, 60, 75, 21, Game\Stage\List\Folder6$, "", True)
		stg7button				= GUI_CreateButton(grp3, 90, 60, 75, 21, Game\Stage\List\Folder7$, "", True)
		stg8button				= GUI_CreateButton(grp3, 170, 60, 75, 21, Game\Stage\List\Folder8$, "", True)
		stg9button				= GUI_CreateButton(grp3, 250, 60, 75, 21, Game\Stage\List\Folder9$, "", True)
		stg10button				= GUI_CreateButton(grp3, 330, 60, 75, 21, Game\Stage\List\Folder10$, "", True)
		stgrefresh				= GUI_CreateButton(grp3, 410, 60, 60, 21, "Refresh", "", True)
		
	;Next
		CreditTimer = MilliSecs() + 109000
	Game\CreatedMenu = 1
		
	EndIf
	
	;While Not KeyHit(1) Or (GUI_AppEvent() = testbutton) Or (KeyHit(KEY_ENTER)) Or (GUI_AppEvent() = gobutton)
	Repeat
		
		If (GUI_Message(radsonic, "GetChecked")=True) Then ModelSelect$ = GUI_Message(edit2, "GetText") : CharType = CHARACTER_SONIC : UseStageChar = 0; : DebugLog("Using Sonic Type") : 
		If (GUI_Message(radtails, "GetChecked")=True) Then ModelSelect$ = GUI_Message(edit3, "GetText") : CharType = CHARACTER_TAILS : UseStageChar = 0; : DebugLog("Using Tails Type")
		If (GUI_Message(radknuckles, "GetChecked")=True) Then ModelSelect$ = GUI_Message(edit4, "GetText") : CharType = CHARACTER_KNUCKLES : UseStageChar = 0; : DebugLog("Using Knuckles Type")
		If (GUI_Message(raddefchar, "GetChecked")=True) Then UseStageChar = 1; : DebugLog("Using default character") : Else UseStageChar = 0
		
		CreditView=0
		Input_Lock = False
		Paused = 0
		HidePointer()
		MakeMenu()
		
		ChannelVolume (Game\MusicChn, 1)
		
		If (GUI_AppEvent() = htrackhelpbutton) Then PlaySound(Sound_MenuMessage) : GUI_MsgBox("Tool Help", "This will not work If there is no 'HeadTrack' bone in your model, parented To your model's head bone" + Chr(10) + "Refer to the model included if you need help")
		If (GUI_AppEvent() = stagehelpbutton) Then PlaySound(Sound_MenuMessage) : GUI_MsgBox("Tool Help", "Type the name of your stage (Found in the 'Stages' folder) and press ENTER" + Chr(10) + "Or the 'GO' button to start the game!" + Chr(10) + "You can also press F1 to start the Test Stage")
		
		
		If GUI_AppEvent() = creditbutton Then
			
	;		CreditView = 1
			PlaySound(Sound_MenuMessage) : GUI_MsgBox("Credits", "Mod, various modeling, and most other things: Arrow/LinkSonic5" + Chr(10) + "Online Code: SamCrossette and 3DSawnikku" + Chr(10) +"Various Music: Teodor/MrSonic699" + Chr(10) + "Base engine by BlitzSonic Team: Streak ThunderStorm, Mark 'Corй', Mista Ed," + Chr(10) + "Damizean, who optimised the original engine For Blitz3D" + Chr(10) + "GUI And Particle System: Devil-Engines" + Chr(10) + "Special thanks:" + Chr(10) + "8BitDragon, 11SuperSonic11/CarrierHack, ChaosShadic456," + Chr(10) + "OZCrashSonic, AxiumGhost13, Gistix, The 'OUR BlitzSonic Game' Community" + Chr(10) + "And you :)")
	EndIf
		
		
	;	If CreditView=1 Then
	
	; UPDATE TOTAKA'S SONG
	If (CreditTimer < MilliSecs()) And (ChannelPlaying(Channel_Totaka)=False) Then
	;	ChannelVolume(Game\Stage\Properties\Music, 0)
	;	StopChannel(Game\MusicChn)
		Channel_Totaka = PlaySound(Sound_Totaka)
		CreditTimer = MilliSecs() + 185000
	EndIf
	
	If (ChannelPlaying(Channel_Totaka)=True) Then
		ChannelVolume(Game\MusicChn,0.3)
	ElseIf (ChannelPlaying(Channel_Totaka)=False)
		ChannelVolume(Game\MusicChn,1)
	EndIf
	;	EndIf
	
	
	; HOST ONLINE GAME
	If (GUI_AppEvent() = hostbutton) Then
		
		EditPath$ = GUI_Message(edit1, "GetText")
		If (EditPath$ <> "") Then
		
		My_Port = 2000
		MPStage = GUI_Message(edit1, "GetText")
		BP_HostSession (GUI_Message(editname, "GetText"),10,1,2222,100)
		
		
		
		
			StartGame(EditPath$)
			Exit
		EndIf
		
	EndIf
	
	; JOIN ONLINE GAME
	If (GUI_AppEvent() = joinbutton) Then
		My_Port = 2000
		EditPath$ = GUI_Message(edit1, "GetText")
		
		BP_JoinSession (GUI_Message(editname, "GetText"),My_Port,GUI_Message(editipjoin, "GetText"),2222)
		
		StartGame(EditPath$)
		Exit
	EndIf
	
	; REFRESH STAGES
	If (GUI_AppEvent() = stgrefresh) Then RefreshStages()
	
	; START STAGE
	If (GUI_AppEvent() = gobutton) Or (KeyHit(KEY_ENTER)) And Game\Gameplay\TypingStage = 1 Then
			
			EditPath$ = GUI_Message(edit1, "GetText")
			
	;		ModelSelect$ = GUI_Message(edit2, "GetText")
	;		DebugLog ("Character name is: " + ModelSelect$)
			
			
			If (EditPath$ <> "") Then
			
			StartGame(EditPath$)
			
			Exit
		EndIf
	EndIf
	
	If (GUI_AppEvent() = clearbutton) Then EditPath$ = GUI_Message(edit1, "SetText", "")
	
	; START TEST STAGE
	If (GUI_AppEvent() = testbutton Or KeyHit(KEY_F1)) And Game\Gameplay\TypingStage = 1 Then
		
		StartGame("Test")
		
	;	ModelSelect$ = GUI_Message(edit2, "GetText")
	;	DebugLog ("Character name is: " + ModelSelect$)
			
			Exit
		EndIf
		
		If ((KeyHit(1)) Or (GUI_AppEvent() = exitbutton)) And Game\Gameplay\TypingStage = 1 Then
			Leave = 1
			Exit
		EndIf
			
		If (GUI_AppEvent() = stg1button) Then StartGame(Game\Stage\List\Folder1$) : Exit
		If (GUI_AppEvent() = stg2button) Then StartGame(Game\Stage\List\Folder2$) : Exit
		If (GUI_AppEvent() = stg3button) Then StartGame(Game\Stage\List\Folder3$) : Exit
		If (GUI_AppEvent() = stg4button) Then StartGame(Game\Stage\List\Folder4$) : Exit
		If (GUI_AppEvent() = stg5button) Then StartGame(Game\Stage\List\Folder5$) : Exit
		If (GUI_AppEvent() = stg6button) Then StartGame(Game\Stage\List\Folder6$) : Exit
		If (GUI_AppEvent() = stg7button) Then StartGame(Game\Stage\List\Folder7$) : Exit
		If (GUI_AppEvent() = stg8button) Then StartGame(Game\Stage\List\Folder8$) : Exit
		If (GUI_AppEvent() = stg9button) Then StartGame(Game\Stage\List\Folder9$) : Exit
		If (GUI_AppEvent() = stg10button) Then StartGame(Game\Stage\List\Folder10$) : Exit
		
;		If (GUI_AppEvent() = gobutton) Or (GUI_AppEvent() = testbutton) Or (KeyHit(KEY_ENTER)) Then 
		
	;Wend
	;Until KeyHit(1); Or (GUI_AppEvent() = testbutton) Or (KeyHit(KEY_ENTER)) Or (GUI_AppEvent() = gobutton)
	Forever
	
	If Leave = 1 Then End
	
	
	
	HeadTrackingEnabled = GUI_Message(htrackbox, "GetChecked")
;	If HeadTrackingEnabled1 = 1 Then
;		HTrackEnabled$ = True
;		HeadTrackingEnabled = HeadTrackingEnabled1
;	Else
;		HTrackEnabled$ = False
;EndIf

Debug = GUI_Message(debugbox, "GetChecked")
;If Debug1 = 1 Then
;	Debug = Debug1
;		DebugString$ = True
;	Else
;		DebugString$ = False
;EndIf
	
BBlastEnabled = GUI_Message(bblastbox, "GetChecked")
;If BBlastEnabled1 = 1 Then
;	BBlast$ = True
;	BBlastEnabled = BBlastEnabled1
;Else
;	BBlast$ = False
;	EndIf

FXEnabled = GUI_Message(fxbox, "GetChecked")

Gameplay_Camera_TargetPOV = GUI_Message(soniccambox, "GetChecked")

TiltingEnabled = GUI_Message(tiltbox, "GetChecked")

FootStepEnabled = GUI_Message(footstepbox, "GetChecked")
	
	fileout = WriteFile("Data\Defaults.dat")
	
	If (GUI_Message(edit2, "GetText") <> "") Then
		WriteLine(fileout, GUI_Message(edit2, "GetText"))
	Else
		WriteLine(fileout, "Sonic")
	EndIf
	WriteLine(fileout, Debug)
	WriteLine(fileout, HeadTrackingEnabled)
	WriteLine(fileout, BBlastEnabled)
	WriteLine(fileout, EditPath$)
	WriteLine(fileout, Gameplay_Camera_TargetPOV)
	WriteLine(fileout, TiltingEnabled)
	WriteLine(fileout, FootStepEnabled)
	WriteLine(fileout, FXEnabled)
	If (GUI_Message(edit3, "GetText") <> "") Then
		WriteLine(fileout, GUI_Message(edit3, "GetText"))
	Else
		WriteLine(fileout, "Tails")
	EndIf
	If (GUI_Message(edit4, "GetText") <> "") Then
		WriteLine(fileout, GUI_Message(edit4, "GetText"))
	Else
		WriteLine(fileout, "Knuckles")
	EndIf
	WriteLine(fileout, (GUI_Message(radsonic, "GetChecked")))
	WriteLine(fileout, (GUI_Message(radtails, "GetChecked")))
	WriteLine(fileout, (GUI_Message(radknuckles, "GetChecked")))
	WriteLine(fileout, (GUI_Message(raddefchar, "GetChecked")))
	WriteLine(fileout, (GUI_Message(betamodebox, "GetChecked")))
	WriteLine(fileout, GUI_Message(editipjoin, "GetText"))
	WriteLine(fileout, GUI_Message(editname, "GetText"))
	;GUI_Message(radsonic, "GetChecked")
	;GUI_Message(radtails, "GetChecked")
	;GUI_Message(radknuckles, "GetChecked")
	;GUI_Message(raddefchar, "GetChecked")
	
	CloseFile(fileout)
	
	Game_Update()
	
	GUI_Message(Win, "Close")
	
	
	
;	GUI_FreeGUI()
;	Game\CreatedMenu = 0
;	End
	
End Function

Function Menu_SkipType()
	If MouseDown(2)
		FlushMouse():FlushKeys();:Delay 150
		geted$=""
		Return geted$
		
	EndIf
	
	If MouseDown(1) And geted$<>""
		FlushMouse():FlushKeys();:Delay 150
		Return geted$
	EndIf
	
	If (geted$<>"") Then
		SetFont SystemFont
		Game\Path$	= "Stages/"+geted$+"/"
		Game\State = GAME_STATE_START
		PlaySound Sound_SelectLevel
		HidePointer()
		ChannelVolume (Game\MusicChn, 0.4)
		Game\StageSelecting = 0
	EndIf
	
	Flip
	get=0
End Function

Function Menu_Cancel()
	Game\TypeStage = 0
	FlushMouse():FlushKeys();:Delay 150	
	If get=27 Then geted$=""
	Return geted$
	
End Function

Function MakeMenu()

	ClsColor(0,0,0)
	Cls()
	
	StartDraw()

	; Setup rendering methods
SetBlend(FI_ALPHABLEND)
SetAlpha(0.7)
SetScale(1, 1)
SetColor(0, 0, 0)
DrawRect(0, 0, GAME_WINDOW_W, GAME_WINDOW_H, 1)

SetBlend(FI_ALPHABLEND)
SetAlpha(1.0)
SetScale(GAME_WINDOW_SCALE#, GAME_WINDOW_SCALE#)
SetColor(255, 255, 255)

DrawImageEx(Interface_Menu, 0*GAME_WINDOW_SCALE#, 0*GAME_WINDOW_SCALE#)
	;ScaleImageEx(Interface_Menu, GAME_WINDOW_W, GAME_WINDOW_H)

;	If (Game\TypeStage = 0) Then
;		UpdateAll()
;	EndIf

;UpdateAll()

GUI_UpdateGUI()


;GUI_UpdateMenu(Win)
;GUI_UpdateMenu(WinMenu)
;GUI_UpdateMenu(grp10)
;GUI_UpdateMenu(edt1)
;GUI_UpdateWindow()

EndDraw()

Flip()

Game\StageSelecting = 1

End Function

Function UpdateMenu()
	
End Function

Function StartMenu()
	Game\CreatedMenu = 0
	
	Game\State = GAME_STATE_SELECT
End Function

Function StartGame(Path$, Sound = 1)
	SetFont SystemFont
	Game\Path$ = "Stages/" + Path$ + "/"
	
	For d.tDeltaTime = Each tDeltaTime
		If GUI_Message(betamodebox, "GetChecked")=True Then
			
			d\IdealInterval		= 1/(1000/Float(30))
			BetaTapeMode = 1
		Else
			d\IdealInterval		= 1/(1000/Float(d\IdealFPS))
			BetaTapeMode = 0
		EndIf
	Next
	
	
; If file is not found
	If (FileType(Game\Path$+"Stage.xml")=0) Then
		
		; If In-Game
		If (Game\StageSelecting = 0) Then 
			
			MoveMouse(GAME_WINDOW_W Shr 1, GAME_WINDOW_H Shr 1)
			FlushMouse()
			SetFont GameFont
			
		;	Game\Path$ = Path$
		;	Game\Path$ = "Stages/" + Path$ + "/"
		;	Game\State = GAME_STATE_END1
			
; If In-Menu
Else
	Game\CreatedMenu = 0
	DebugLog("Stage Read Error")
	Game\StageError = 1
	Game\State = GAME_STATE_SELECT1
	
;	GUI_MsgBox("Error", "An error occurred while loading the stage you selected." + Chr(10) + "Try To correct any errors you may have made within the stage folder." + Chr(10) + "The file path you typed was: " + Game\Path$)
EndIf




; If file is found
Else
	
	; If In-Game
	If (Game\StageSelecting = 0) Then
		
	;	Game\Path$ = Path$
		Game\Path$ = "Stages/" + Path$ + "/"
		ChannelVolume (Game\MusicChn, 0.4)
		If Sound = 1 Then PlaySound Sound_SelectLevel
		Game\StartGame = 1
		Game\State = GAME_STATE_END : DebugLog("Stage ended in-game")
		
	;If In-Menu
	Else
		
		If (GUI_Message(raddefchar, "GetChecked")=False) Then Load_Characters_List(ModelSelect$)
		
		Game\Path$ = "Stages/" + Path$ + "/"
		Game\StartGame = 0
		Game\State = GAME_STATE_START
		If Sound = 1 Then PlaySound Sound_SelectLevel
		HidePointer()
		ChannelVolume (Game\MusicChn, 0.4)
		Game\StageSelecting = 0
	
		
	
EndIf
	
EndIf
	
End Function
	
	
	; ---------------------------------------------------------------------------------------------------------
	; Game_Stage_Start
	; ---------------------------------------------------------------------------------------------------------
	Function Game_Stage_Start()
		; At startup, load up current stage from the list. First, obtain the final path to the stage folder
		; and hold on a string. Then, parse the Stage.xml file to find out all the specifications
		; and objects on the stage.
		
	;	For d.tDeltaTime = Each tDeltaTime
	;		d\IdealInterval		= 1/(1000/Float(d\IdealFPS))
		;	d\IdealInterval		= 1/(1000/Float(50))
	;	Next
		
	;	CubeCamera = CreateCamera()
	;	CameraRange(CubeCamera, 		0.1, 999999999)
	;	CameraProjMode CubeCamera, 0 ;Setting ProjectionMode to 0 deactives the camera
		
		 ; ----
	;	CubeMapTex = CreateTexture (CubeSize, CubeSize, 1+16+32+256 + FE_RENDER + FE_ZRENDER) 
	;	
	;	TextureBlend CubeMapTex, FE_PROJECT          
	;	
	;	PositionTexture CubeMapTex, 0.5, 0.5         
	;	
	;	ScaleTexture CubeMapTex, 2, -2         
		
	;	If CubeMapTex Then FreeTexture CubeMapTex
	; ----
	;	CubeMapTex = CreateTexture (w, h, 1+16+32+256 + FE_RENDER + FE_ZRENDER)		; <<<<	Создадим текстуру для 3Д рендеринга	
																		; create texture for render reflections	
	;	TextureBlend CubeMapTex, FE_PROJECT										; <<<<	Новый бленд  для наложения текстуры как проекции
																		; new blend for 2D project texture
	;	PositionTexture CubeMapTex, 0.5, 0.5										; <<< Смещение = в центр 0.5
																		; set 2D projection texture to center of screen
	;	ScaleTexture CubeMapTex, 2, -2											; по Y скейл отрицательный, так как отражение зеркально
																		; negative scale on Y axis (since reflection mirror)
		
		;CreateRefractTextures(512, 512)
 ; ----
		
		WaterSize = 150
		SizeMultiplyer = 200
		
		GUI_Message(ObjMenu_Win, "Close")
		PurgeTransitions()
		SetBuffer(BackBuffer())
		
		; ------------
		If FXEnabled = 1 Then
			
		;	CreateShadow 2							; <<<<<   create shadows (with quality=1) and customize his characteristics
		;	ShadowRange 75							; <<<<<   set shadow range (50x50)
		;	ShadowPower 0.7	
			
			InitPostProcess()
			
		;Shadow Caster Positon
		; create light
			LightPivot = CreatePivot()
			TurnEntity LightPivot, 100, 180, 0
		;AmbientLight 64, 64, 64
			
		;	Light = CreateLight(LightPivot)
		;	TurnEntity Light, 60, 0, 0
			
		;	ShadowLight Light
			
		EndIf
		
		Game\Transitioning = 0
		Game\StageSelecting = 0
		
		Game\Stage\LoadMessage = Rand(0,5)
		;DEFAULT VALUES
		Game\Stage\Properties\Hub = 0
		Game\Gameplay\TypingStage = 0
		HidePointer()
		; -------------
		
	
		;Path$		= "Stages/"+Game\Stage\List\Folder$+"/"

		; Create root entity for all the stage entities
		Game\Stage\Root 			= CreatePivot()
		Game\Stage\Gravity			= CreatePivot()
		Game\Stage\GravityAlignment	= Vector(0, 1, 0)

		; Create camera
		camera.tCamera = Camera_Create()
		
		; Parse up Stage.xml file and retrieve root node. Find out if any parse errors ocurred while loading
		; and if so, stop the game.
		Game_Stage_UpdateProgressBar("Parsing stage XML", 0)
		
		RootNode	= xmlLoad(Game\Path$+"Stage.xml")
		
		
		
	;	If (RootNode <> 1) Then PlaySound(Sound_Error) : GUI_MsgBox("Stage Does Not Exist", "Please re-type the stage name, the one you typed could not be found.") : Game\Pickstage = 1 : Game\State=GAME_STATE_SELECT1 : Game\Gameplay\TypingStage = 1; : Game\Path$ = "Stages/" + Game\Stage\Hub$ + "/"
	;	If (RootNode = 1) Then Game_State = GAME_STATE_SETUP
		
;End Function
		
;Function Game_Stage_Setup()
	;	If (xmlErrorCount()>0) Then RuntimeError("An error occurred while loading the stage you selected. Try to correct any errors you may have made within the stage folder. The file path you typed was: " + Game\Path$)
	;	If (xmlErrorCount()>0) And (xmlLoad = 1) Then PlaySound(Sound_Error) : Game_State = GAME_STATE_END;RuntimeError("An error occurred while loading the stage you selected. Try to correct any errors you may have made within the stage folder. The file path you typed was: " + Game\Path$)
		;("Game_Update_Stage() -> [Start] Error loading stage '"+Game\Stage\List\Folder$+"' xml. Parse error.")

		; Retrieve stage settings. From here on, this programming is kinda shitty, as the program presumes
		; all the nodes we're going to access to exist.
		For i = 1 To xmlNodeChildCount(RootNode)
			; Get child node
			RootChildNode = xmlNodeChild(RootNode, i)
			
			Game\Stage\Properties\WaterTexture = LoadTexture("Textures\water\1.png")
	
			; Find out wich type it is.
			Select xmlNodeNameGet$(RootChildNode)
				Case "information"
					
					; Retrieve stage information such as stage's name, music and ambient light.
					For j = 1 To xmlNodeChildCount(RootChildNode)
						; Update progress bar
						Game_Stage_UpdateProgressBar("Loading stage information", Float#(j)/Float#(xmlNodeChildCount(RootChildNode)))
						
						; Retrieve child node
						InformationChildNode = xmlNodeChild(RootChildNode, j)
		
						; Find out what it is and acquire.
						Select xmlNodeNameGet$(InformationChildNode)
							Case "name"
								Game\Stage\Properties\Name$ = xmlNodeDataGet(InformationChildNode)
							Case "startmusic"
								Game\Stage\Properties\StartMusic = LoadSound("Music/"+xmlNodeDataGet(InformationChildNode))
							;	LoopSound(Game\Stage\Properties\StartMusic)
							Case "music"
								Game\Stage\Properties\Music = LoadSound("Music/"+xmlNodeDataGet(InformationChildNode))
								LoopSound(Game\Stage\Properties\Music)
							Case "ambientlight"
								AmbientLight(xmlNodeAttributeValueGet(InformationChildNode, "r"), xmlNodeAttributeValueGet(InformationChildNode, "g"), xmlNodeAttributeValueGet(InformationChildNode, "b"))
							Case "rain"
								If xmlNodeAttributeValueGet(InformationChildNode, "on") = 1 Then
									Game\Stage\Properties\Rain = 1
								ElseIf xmlNodeAttributeValueGet(InformationChildNode, "on") = 0 Then
									Game\Stage\Properties\Rain = 0
								EndIf
								
							Case "snow"
								If xmlNodeAttributeValueGet(InformationChildNode, "on") = 1 Then
									Game\Stage\Properties\Snow = 1
								ElseIf xmlNodeAttributeValueGet(InformationChildNode, "on") = 0 Then
									Game\Stage\Properties\Snow = 0
								EndIf
								
							Case "hub"
								
								If xmlNodeAttributeValueGet(InformationChildNode, "on") = 1 Then
									Game\Stage\Properties\Hub = 1
								ElseIf xmlNodeAttributeValueGet(InformationChildNode, "on") = 0 Then
									Game\Stage\Properties\Hub = 0
								EndIf
								
							Case "skybox"
								Game\Stage\Properties\SkyBox = LoadMesh(Game\Path$+xmlNodeDataGet(InformationChildNode))
								ScaleEntity(Game\Stage\Properties\SkyBox, 0.1, 0.1, 0.1)
						;		EntityColor(Game\Stage\Properties\SkyBox, 255, 255, 255)
								EntityFX(Game\Stage\Properties\SkyBox, 9)										
								EntityOrder(Game\Stage\Properties\SkyBox, 1)
								
						;	Case "sun"
						;		positionx# = 0
						;		positiony# = 2000
						;		positionz# = 0
						;		Game\Stage\Properties\Sun = LoadMesh(Game\Path$+xmlNodeAttributeValueGet(InformationChildNode, "mesh"))
						;		ScaleEntity(Game\Stage\Properties\Sun, 0.1, 0.1, 0.1)
						;		EntityColor(Game\Stage\Properties\Sun, 255, 255, 255)
						;		EntityOrder(Game\Stage\Properties\Sun, 1)
						;		EntityFX(Game\Stage\Properties\Sun, 1+8+32)
						;		EntityPickMode Game\Stage\Properties\Sun, 1
						;		EntityParent(Game\Stage\Properties\Sun, Game\Stage\Properties\SkyBox)
						;		ScenePosition = xmlNodeFind("position", SceneChildNode)
						;		If (ScenePosition<>0) Then PositionEntity(Game\Stage\Properties\Sun, Float(xmlNodeAttributeValueGet(ScenePosition, "x")), Float(xmlNodeAttributeValueGet(ScenePosition, "y")), Float(xmlNodeAttributeValueGet(ScenePosition, "z")))
								
							Case "water"
								If xmlNodeAttributeValueGet(InformationChildNode, "on") = 1 Then
							;		WaterMesh$   = xmlNodeAttributeValueGet(InformationChildNode, "mesh")
									WaterScaleX$ = xmlNodeAttributeValueGet(InformationChildNode, "scale_x")
									WaterScaleY$ = xmlNodeAttributeValueGet(InformationChildNode, "scale_y")
									WaterScaleZ$ = xmlNodeAttributeValueGet(InformationChildNode, "scale_z")
									WaterTexture$ = xmlNodeAttributeValueGet(InformationChildNode, "texture")
									
							;		If (WaterMesh$ <> "") Then
							;			Game\Stage\Properties\Water = LoadMesh(Game\Path$+WaterMesh$)
									If (WaterScaleX$ <> "") Then ScaleEntity(Game\Stage\Properties\Water, Float#(WaterScaleX$), BB_EntityScaleY#(Game\Stage\Properties\Water), BB_EntityScaleZ#(Game\Stage\Properties\Water))
									If (WaterScaleY$ <> "") Then ScaleEntity(Game\Stage\Properties\Water, BB_EntityScaleX#(Game\Stage\Properties\Water), Float#(WaterScaleY$), BB_EntityScaleZ#(Game\Stage\Properties\Water))
									If (WaterScaleZ$ <> "") Then ScaleEntity(Game\Stage\Properties\Water, BB_EntityScaleX#(Game\Stage\Properties\Water), BB_EntityScaleY#(Game\Stage\Properties\Water), Float#(WaterScaleZ$))
									Game\Stage\Properties\WaterLevel = xmlNodeAttributeValueGet(InformationChildNode, "level")
							;		Else
										Game\Stage\Properties\Water = CreatePlane()
										Game\Stage\Properties\WaterLevel = xmlNodeAttributeValueGet(InformationChildNode, "level")
										TranslateEntity(Game\Stage\Properties\Water, 0, Game\Stage\Properties\WaterLevel, 0)
							;		End If
									If (WaterTexture$ <> "") Then
										Game\Stage\Properties\WaterTexture = LoadTexture(Game\Path$+WaterTexture$)
										EntityTexture(Game\Stage\Properties\Water, Game\Stage\Properties\WaterTexture)
									End If

									Game\Stage\Properties\WaterDistortion = LoadAnimTexture("Textures/Water_Bump.png", 0, 64, 64, 0, 36)
									Game\Stage\Properties\WaterFX = FxManager_RegisterFxMeshPostProcess(Game\Stage\Properties\Water, Game\Stage\Properties\WaterDistortion, Vector(0, 0, 0), Vector(0, 0, 0), Vector(0, 0, 0), 0.4, 0.3)
									ScaleTexture(Game\Stage\Properties\WaterDistortion, 1, 1)
									
									CubeMapTex = Wat_CreateTextures(512, 512)
									
								;	CMWater = CreateWater(WaterSize, SizeMultiplyer);Init the mesh creation function in the include file, it isn't perfect, I just done it for testing, will look a bit weird...
									CMWater = CreatePlane()
									
								;	PositionMesh CMWater, -size / 2.0, 0, -size / 2.0
								;	UpdateNormals CMWater
								;	EntityFX CMWater, 1+17
									ScaleEntity CMWater, size_multiplicator#, 1, size_multiplicator#
									EntityColor CMWater, 200, 200, 255
									
									
									EntityTexture CMWater, CubeMapTex;, 0, 1
								;	EntityAlpha CMWater,.35
									
									PositionEntity(CMWater, 0, Game\Stage\Properties\WaterLevel-0.5, 0) ;If you see a glitch because one entity is inside another, just higher the -0.5
									
								;	WaterClipplane = CreateClipplane (  Game\Stage\Properties\Water  )					; <<<<< при создании сразу можно выровнять по заданному ентити
															; align clipplane by water plane
								;	AlignClipplane WaterClipplane, Game\Stage\Properties\Water
									
								EndIf
								
								
								
								
								
								
				;			Case "fog"
				;				If xmlNodeAttributeValueGet(InformationChildNode, "on") = 1 Then
				;					SceneFogAmmount = xmlNodeFind("ammount", SceneChildNode)
				;					If (SceneFogAmmount <> 0) Then
				;						Game\Stage\Properties\FogAmmountNear# = Float(xmlNodeAttributeValueGet(SceneFogAmmount, "near"))
				;						Game\Stage\Properties\FogAmmountFar# = Float(xmlNodeAttributeValueGet(SceneFogAmmount, "far"))
				;					End If
				;					SceneFogColor = xmlNodeFind("color", SceneChildNode)
				;					If (SceneFogColor<>0) Then
				;						Game\Stage\Properties\FogColorR = Float(xmlNodeAttributeValueGet(SceneFogColor, "r"))
				;						Game\Stage\Properties\FogColorG = Float(xmlNodeAttributeValueGet(SceneFogColor, "g"))
				;						Game\Stage\Properties\FogColorB = Float(xmlNodeAttributeValueGet(SceneFogColor, "b"))
				;					End If
				;					CameraFogRange(camera\Entity, Game\Stage\Properties\FogAmmountNear#, Game\Stage\Properties\FogAmmountFar#)
				;					CameraFogColor(camera\Entity, Game\Stage\Properties\FogColorR, Game\Stage\Properties\FogColorG, Game\Stage\Properties\FogColorB)
				;				End If
				;				If xmlNodeAttributeValueGet(InformationChildNode, "on") = 0 Then
				;					Game\Stage\Properties\FogColorR = 170
				;					Game\Stage\Properties\FogColorG = 208
				;					Game\Stage\Properties\FogColorB = 255
				;					Game\Stage\Properties\FogAmmountNear# = 940
				;					Game\Stage\Properties\FogAmmountFar# = 1600
				;					CameraFogRange(camera\Entity, Game\Stage\Properties\FogAmmountNear#, Game\Stage\Properties\FogAmmountFar#)
				;					CameraFogColor(camera\Entity, Game\Stage\Properties\FogColorR, Game\Stage\Properties\FogColorG, Game\Stage\Properties\FogColorB)
				;				End If
				;				If xmlNodeAttributeValueGet(InformationChildNode, "affect_skybox") = 1 Then
				;					EntityFX(Game\Stage\Properties\SkyBox, 11)
				;					For index=1 To CountSurfaces(Game\Stage\Properties\SkyBox)
				;						surf=GetSurface(Game\Stage\Properties\SkyBox,index)
				;						For vertex=0 To CountVertices(surf)
				;							v.Vertice=New Vertice
				;							v\SurfHandle=surf
				;							v\Index=vertex
				;							v\R=Game\Stage\Properties\FogColorR
				;							v\G=Game\Stage\Properties\FogColorG
				;							v\B=Game\Stage\Properties\FogColorB
				;							VertexColor(v\SurfHandle,v\Index,v\R,v\G,v\B)
				;						Next
				;					Next
				;				End If

						End Select 
					Next
		
					
				Case "scene"
					; Retrieve stage's scene specifications
					For j = 1 To xmlNodeChildCount(RootChildNode)
						
						; Retrieve child node
						SceneChildNode = xmlNodeChild(RootChildNode, j)
						
						; Find out wich kind of scene entity it is.
						Select xmlNodeNameGet$(SceneChildNode)
							Case "mesh"								
								; Retrieve mesh properties information
								MeshFilename$	= xmlNodeAttributeValueGet(SceneChildNode, "filename") 
								MeshVisible		= xmlNodeAttributeValueGet(SceneChildNode, "visible")
								MeshCollision	= xmlNodeAttributeValueGet(SceneChildNode, "collision")
								MeshDie			= xmlNodeAttributeValueGet(SceneChildNode, "die")
								MeshHurt		= xmlNodeAttributeValueGet(SceneChildNode, "hurt")
								Number			= xmlNodeAttributeValueGet(SceneChildNode, "number")
								Intersect		= xmlNodeAttributeValueGet(SceneChildNode, "intersect")
								CamMesh			= xmlNodeAttributeValueGet(SceneChildNode, "cammesh")
								Water			= xmlNodeAttributeValueGet(SceneChildNode, "water")
								Lava			= xmlNodeAttributeValueGet(SceneChildNode, "lava")
								Rail			= xmlNodeAttributeValueGet(SceneChildNode, "rail")
								Ledge			= xmlNodeAttributeValueGet(SceneChildNode, "ledge")
								

								; Update progress bar
								Game_Stage_UpdateProgressBar("Loading scene mesh "+j+": "+MeshFilename$, Float#(j)/Float#(xmlNodeChildCount(RootChildNode)))
						
								; Load mesh and set visibility. Depending if the mesh should
								; cast collisions, set alpha to 0 or hide the entity. After this
								; set collision type.
								Mesh = LoadMesh(Game\Path$+MeshFilename$, Game\Stage\Root)
								
								If (FXEnabled = 1) Then
							;	FirstShadowTexture = ShadowTexture()						; <<<<<   get first shadow texture (shadow map) 
							;	EntityTexture Mesh, FirstShadowTexture, 0, 1
							EndIf
								
								If (MeshVisible = False) Then
									If (MeshCollision = True) Then
										EntityAlpha(Mesh, 0.0)
									Else
										HideEntity(Mesh)
									End If
								End If
								If (MeshCollision = True) Then
									EntityType(Mesh, COLLISION_WORLD_POLYGON_ALIGN)
									EntityPickMode(Mesh, 2)
								End If
								
								If (Intersect = True) Then
									EntityType(Mesh, COLLISION_WORLD_INTERSECTTEST)
								;	EntityPickMode(Mesh, 2)
									
									Struct.MeshStructure = New MeshStructure
									Struct\SoundEntity = Mesh
								End If
								
								If (CamMesh = True) Then
									EntityType(Mesh, COLLISION_WORLD_CAM)
								;	EntityPickMode(Mesh, 2)
									
									Struct.MeshStructure = New MeshStructure
									Struct\CamEntity = Mesh
								End If
								
								If (MeshHurt = True) Then
									EntityType(Mesh, COLLISION_WORLD_HURT)
									EntityPickMode(Mesh, 2)
								End If
								
								If (MeshDie = True) Then
									EntityType(Mesh, COLLISION_WORLD_DIE)
									EntityPickMode(Mesh, 2)
									
								End If
								
								If (Rail = True) Then
									EntityType(Mesh, COLLISION_WORLD_RAIL)
									EntityPickMode(Mesh, 2)
								EndIf
								
								If (Ledge = True) Then
									EntityType(Mesh, COLLISION_WORLD_LEDGE)
									EntityPickMode(Mesh, 2)
								EndIf
								
								If (Water = True) Then
							;		EntityType(Mesh, COLLISION_WORLD_DIE)
							;		EntityPickMode(Mesh, 2)
							;		Game\Stage\Properties\WaterTexture = LoadTexture(Game\Path$+WaterTexture$)
									Struct.MeshStructure = New MeshStructure
									Struct\WaterEntity = Mesh
									EntityTexture(Game\Stage\Properties\Water, Game\Stage\Properties\WaterTexture)
								End If
								
								If (Lava = True) Then
							;		EntityType(Mesh, COLLISION_WORLD_DIE)
							;		EntityPickMode(Mesh, 2)
							;		Game\Stage\Properties\WaterTexture = LoadTexture(Game\Path$+WaterTexture$)
									Struct.MeshStructure = New MeshStructure
									Struct\LavaEntity = Mesh
									EntityTexture(Game\Stage\Properties\Lava, Game\Stage\Properties\LavaTexture)
								End If
								

						;		; Create structure
								Struct.MeshStructure = New MeshStructure
								Struct\Entity = Mesh
								
								; Setup position, rotation and scale.
								ScenePosition = xmlNodeFind("position", SceneChildNode)
								If (ScenePosition<>0) Then PositionEntity(Mesh, Float(xmlNodeAttributeValueGet(ScenePosition, "x")), Float(xmlNodeAttributeValueGet(ScenePosition, "y")), Float(xmlNodeAttributeValueGet(ScenePosition, "z")))
								SceneRotation = xmlNodeFind("rotation", SceneChildNode)
								If (SceneRotation<>0) Then RotateEntity(Mesh, xmlNodeAttributeValueGet(SceneRotation, "pitch"), xmlNodeAttributeValueGet(SceneRotation, "yaw"), xmlNodeAttributeValueGet(SceneRotation, "roll"))
								SceneScale = xmlNodeFind("scale", SceneChildNode)
								If (SceneScale<>0) Then ScaleEntity(Mesh, Float(xmlNodeAttributeValueGet(SceneScale, "x")), Float(xmlNodeAttributeValueGet(SceneScale, "y")), Float(xmlNodeAttributeValueGet(SceneScale, "z")))
								
								
								SceneCamPos = xmlNodeFind("campos", SceneChildNode)
								If (SceneCamPos<>0) Then
								CamPosX# = Float(xmlNodeAttributeValueGet(SceneCamPos, "x"))
								CamPosY# = Float(xmlNodeAttributeValueGet(SceneCamPos, "y"))
								CamPosZ# = Float(xmlNodeAttributeValueGet(SceneCamPos, "z"))
							EndIf
							
							
							SceneCamRot = xmlNodeFind("camrot", SceneChildNode)
							If (SceneCamRot<>0) Then
								CamRotX# = Float(xmlNodeAttributeValueGet(SceneCamRot, "pitch"))
								CamRotY# = Float(xmlNodeAttributeValueGet(SceneCamRot, "yaw"))
								CamRotZ# = Float(xmlNodeAttributeValueGet(SceneCamRot, "roll"))			
							EndIf
							
								
								; Attributes
								SceneAttributes = xmlNodeFind("attributes", SceneChildNode)
								If (SceneAttributes <> 0) Then
									SceneAttributesAutofade = xmlNodeFind("autofade", SceneAttributes)
									If (SceneAttributesAutofade <> 0) Then EntityAutoFade(Mesh, Float(xmlNodeAttributeValueGet(SceneAttributesAutoFade, "near")), Float(xmlNodeAttributeValueGet(SceneAttributesAutoFade, "far")))
									SceneAttributesColor = xmlNodeFind("color", SceneAttributes)
									If (SceneAttributesColor <> 0) Then EntityColor(Mesh, Float(xmlNodeAttributeValueGet(SceneAttributesColor, "r")), Float(xmlNodeAttributeValueGet(SceneAttributesColor, "g")), Float(xmlNodeAttributeValueGet(SceneAttributesColor, "b")))
									SceneAttributesOrder = xmlNodeFind("order", SceneAttributes)
									If (SceneAttributesOrder <> 0) Then EntityOrder(Mesh, xmlNodeDataGet(SceneAttributesOrder))
									SceneAttributesEffects = xmlNodeFind("effects", SceneAttributes)
									If (SceneAttributesEffects <> 0) Then EntityFX(Mesh, xmlNodeDataGet(SceneAttributesEffects))
									SceneAttributesBlend = xmlNodeFind("blend", SceneAttributes)
									If (SceneAttributesBlend <> 0) Then EntityBlend(Mesh, xmlNodeDataGet(SceneAttributesBlend))
								End If
							Case "animmesh"

								; Retrieve mesh properties information
								MeshFilename$	= xmlNodeAttributeValueGet(SceneChildNode, "filename") 
								MeshVisible		= xmlNodeAttributeValueGet(SceneChildNode, "visible")
								MeshCollision	= xmlNodeAttributeValueGet(SceneChildNode, "collision")
								MeshDie			= xmlNodeAttributeValueGet(SceneChildNode, "die")
								MeshHurt		= xmlNodeAttributeValueGet(SceneChildNode, "hurt")
								MeshAnimated	= xmlNodeAttributeValueGet(SceneChildNode, "animated")
								Number			= xmlNodeAttributeValueGet(SceneChildNode, "number")
								Intersect		= xmlNodeAttributeValueGet(SceneChildNode, "intersect")
								Water			= xmlNodeAttributeValueGet(SceneChildNode, "water")
								Lava			= xmlNodeAttributeValueGet(SceneChildNode, "lava")
								Rail			= xmlNodeAttributeValueGet(SceneChildNode, "rail")
								Ledge			= xmlNodeAttributeValueGet(SceneChildNode, "ledge")

								; Update progress bar
								Game_Stage_UpdateProgressBar("Loading scene animmesh "+j+": "+MeshFilename$, Float#(j)/Float#(xmlNodeChildCount(RootChildNode)))
								
								; Load mesh and set visibility. Depending if the mesh should
								; cast collisions, set alpha to 0 or hide the entity. After this
								; set collision type.
								Mesh = LoadAnimMesh(Game\Path$+MeshFilename$, Game\Stage\Root)
								
								If (FXEnabled = 1) Then
								;	FirstShadowTexture = ShadowTexture()						; <<<<<   get first shadow texture (shadow map) 
								;	EntityTexture Mesh, FirstShadowTexture, 0, 1
								EndIf
								
								If (MeshVisible = False) Then
									If (MeshCollision = True) Then
										EntityAlpha(Mesh, 0.0)
									Else
										HideEntity(Mesh)
									End If
								End If
								If (MeshCollision = True) Then
									RecursiveEntityType(Mesh, COLLISION_WORLD_POLYGON_ALIGN)
									RecursiveEntityPickMode(Mesh, 2)
								End If
								
								If (MeshHurt = True) Then
									EntityType(Mesh, COLLISION_WORLD_HURT)
									EntityPickMode(Mesh, 2)
								End If
								
								If (Intersect = True) Then
									EntityType(Mesh, COLLISION_WORLD_INTERSECTTEST)
								;	EntityPickMode(Mesh, 2)
									
									Struct.MeshStructure = New MeshStructure
									Struct\SoundEntity = Mesh
								End If
								
								If (MeshDie = True) Then
									EntityType(Mesh, COLLISION_WORLD_DIE)
									EntityPickMode(Mesh, 2)
								End If
								
								If (MeshAnimated = 1) Then
									Animate Mesh
								EndIf
								
								If (Rail = True) Then
									EntityType(Mesh, COLLISION_WORLD_RAIL)
									EntityPickMode(Mesh, 2)
								EndIf
								
								If (Water = True) Then
							;		EntityType(Mesh, COLLISION_WORLD_DIE)
							;		EntityPickMode(Mesh, 2)
							;		Game\Stage\Properties\WaterTexture = LoadTexture(Game\Path$+WaterTexture$)
									Struct.MeshStructure = New MeshStructure
									Struct\WaterEntity = Mesh
									EntityTexture(Game\Stage\Properties\Water, Game\Stage\Properties\WaterTexture)
								End If
								
								If (Ledge = True) Then
									EntityType(Mesh, COLLISION_WORLD_LEDGE)
									EntityPickMode(Mesh, 2)
								EndIf
								
								If (Lava = True) Then
							;		EntityType(Mesh, COLLISION_WORLD_DIE)
							;		EntityPickMode(Mesh, 2)
							;		Game\Stage\Properties\WaterTexture = LoadTexture(Game\Path$+WaterTexture$)
									Struct.MeshStructure = New MeshStructure
									Struct\LavaEntity = Mesh
								;	EntityTexture(Game\Stage\Properties\Lava, Game\Stage\Properties\LavaTexture)
								End If

								; Create structure
									Struct.MeshStructure = New MeshStructure
									Struct\Entity = Mesh
								
								; Setup position, rotation and scale.
								ScenePosition = xmlNodeFind("position", SceneChildNode)
								If (ScenePosition<>0) Then PositionEntity(Mesh, Float(xmlNodeAttributeValueGet(ScenePosition, "x")), Float(xmlNodeAttributeValueGet(ScenePosition, "y")), Float(xmlNodeAttributeValueGet(ScenePosition, "z")))
								SceneRotation = xmlNodeFind("rotation", SceneChildNode)
								If (SceneRotation<>0) Then RotateEntity(Mesh, xmlNodeAttributeValueGet(SceneRotation, "pitch"), xmlNodeAttributeValueGet(SceneRotation, "yaw"), xmlNodeAttributeValueGet(SceneRotation, "roll"))
								SceneScale = xmlNodeFind("scale", SceneChildNode)
								If (SceneScale<>0) Then ScaleEntity(Mesh, Float(xmlNodeAttributeValueGet(SceneScale, "x")), Float(xmlNodeAttributeValueGet(SceneScale, "y")), Float(xmlNodeAttributeValueGet(SceneScale, "z")))
								; Attributes
								SceneAttributes = xmlNodeFind("attributes", SceneChildNode)
								If (SceneAttributes <> 0) Then
									SceneAttributesAutofade = xmlNodeFind("autofade", SceneAttributes)
									If (SceneAttributesAutofade <> 0) Then RecursiveEntityAutoFade(Mesh, Float(xmlNodeAttributeValueGet(SceneAttributesAutoFade, "near")), Float(xmlNodeAttributeValueGet(SceneAttributesAutoFade, "far")))
									SceneAttributesColor = xmlNodeFind("color", SceneAttributes)
									If (SceneAttributesColor <> 0) Then RecursiveEntityColor(Mesh, Float(xmlNodeAttributeValueGet(SceneAttributesColor, "r")), Float(xmlNodeAttributeValueGet(SceneAttributesColor, "g")), Float(xmlNodeAttributeValueGet(SceneAttributesColor, "b")))
									SceneAttributesOrder = xmlNodeFind("order", SceneAttributes)
									If (SceneAttributesOrder <> 0) Then RecursiveEntityOrder(Mesh, xmlNodeDataGet(SceneAttributesOrder))
									SceneAttributesEffects = xmlNodeFind("effects", SceneAttributes)
									If (SceneAttributesEffects <> 0) Then RecursiveEntityFX(Mesh, xmlNodeDataGet(SceneAttributesEffects))
									SceneAttributesBlend = xmlNodeFind("blend", SceneAttributes)
									If (SceneAttributesBlend <> 0) Then RecursiveEntityBlend(Mesh, xmlNodeDataGet(SceneAttributesBlend))
								End If
								
							Case "ringsmesh"
    ; Retrieve mesh properties information
								MeshFilename$    = xmlNodeAttributeValueGet(SceneChildNode, "filename")
								Mesh=LoadAnimMesh(Path$+MeshFilename$, Game\Stage\Root)
								
    ; Update progress bar
								Game_Stage_UpdateProgressBar("Loading scene ring placement "+j+": "+MeshFilename$, Float#(j)/Float#(xmlNodeChildCount(RootChildNode)))
								
    ; Create structure
								Struct.MeshStructure = New MeshStructure
								Struct\Entity = Mesh
                                
    ; Setup position, rotation and scale.
								ScenePosition = xmlNodeFind("position", SceneChildNode)
								If (ScenePosition<>0) Then PositionEntity(Mesh, Float(xmlNodeAttributeValueGet(ScenePosition, "x")), Float(xmlNodeAttributeValueGet(ScenePosition, "y")), Float(xmlNodeAttributeValueGet(ScenePosition, "z")))
								SceneRotation = xmlNodeFind("rotation", SceneChildNode)
								If (SceneRotation<>0) Then RotateEntity(Mesh, xmlNodeAttributeValueGet(SceneRotation, "pitch"), xmlNodeAttributeValueGet(SceneRotation, "yaw"), xmlNodeAttributeValueGet(SceneRotation, "roll"))
								SceneScale = xmlNodeFind("scale", SceneChildNode)
								If (SceneScale<>0) Then ScaleEntity(Mesh, Float(xmlNodeAttributeValueGet(SceneScale, "x")), Float(xmlNodeAttributeValueGet(SceneScale, "y")), Float(xmlNodeAttributeValueGet(SceneScale, "z")))
								
								mLoadRings(Mesh)
								HideEntity(Mesh)
								
							Case "light"
								; Update progress bar
								Game_Stage_UpdateProgressBar("Loading scene light "+j, Float#(j)/Float#(xmlNodeChildCount(RootChildNode)))
								
								; Retrieve mesh properties information
								;Game\Stage\Properties\Light = 0
								LightType$	= xmlNodeAttributeValueGet(SceneChildNode, "type")
							;	l.tGame_Lights = New tGame_Lights
								
							;	For l.tGame_Lights = Each tGame_Lights
								For i = 0 To 7
								
								Select LightType$
											
										Case "directional"
											If (Game\Lights[i] = Null) Then
												Game\Lights[i] = New tGame_Lights
												Game\Lights[i]\Light = CreateLight(1)
											End If
											
									Case "point"
										If (Game\Lights[i] = Null) Then
											Game\Lights[i] = New tGame_Lights
											Game\Lights[i]\Light = CreateLight(2)
											End If
											
									Case "spot"
										If (Game\Lights[i] = Null) Then
											Game\Lights[i] = New tGame_Lights
											Game\Lights[i]\Light = CreateLight(3)
											End If
											
								End Select
							;	LightRange(Game\Stage\Properties\Light, Float(xmlNodeAttributeValueGet(SceneChildNode, "range")))
								LightRange(Game\Lights[i]\Light, Float(xmlNodeAttributeValueGet(SceneChildNode, "range")))
								
								; Setup position, rotation and scale.
								LightPosition = xmlNodeFind("position", SceneChildNode)
								If (LightPosition<>0) Then PositionEntity(Game\Lights[i]\Light, Float(xmlNodeAttributeValueGet(LightPosition, "x")), Float(xmlNodeAttributeValueGet(LightPosition, "y")), Float(xmlNodeAttributeValueGet(LightPosition, "z")))
								LighRotation = xmlNodeFind("rotation", SceneChildNode)
								If (LighRotation<>0) Then RotateEntity(Game\Lights[i]\Light, xmlNodeAttributeValueGet(LighRotation, "pitch"), xmlNodeAttributeValueGet(LighRotation, "yaw"), xmlNodeAttributeValueGet(LighRotation, "roll"))
								LightCol = xmlNodeFind("color", SceneChildNode)
								If (LightCol<>0) Then LightColor(Game\Lights[i]\Light, xmlNodeAttributeValueGet(LightCol, "r"), xmlNodeAttributeValueGet(LightCol, "g"), xmlNodeAttributeValueGet(LightCol, "b"))
								
							Next
					;	Next
							
							;~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~;
							;	EightBitDragon was here		;
							;~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~;
							; This loads a sound into the global type array called Sounds[256].tSound
							; Arguments are the file name of the sound, and the number in which to place the sound into the array.
							; <sound file="whatever.wav" number="151" />
							; You then play the sound with SoundPlay(Sounds[151])
							; SoundPlay has an optional float argument, used for volume adjustment. SoundPlay(Sounds[151], 1.0)
						Case "sound"
								; Update progress bar
							Game_Stage_UpdateProgressBar("Loading object n."+j+": Sound Effect", Float#(j)/Float#(xmlNodeChildCount(RootChildNode)))
							
								; Set local variables' default values
							file$		= ""
							number		= 128
							
								; Read the data from the xml
							file$ = xmlNodeAttributeValueGet(SceneChildNode, "filename")
							number = xmlNodeAttributeValueGet(SceneChildNode, "number")
							
								; Load the sound into the Sounds array, into the slot specified by the variable called number
							Sounds[number] = SoundLoad(Path$+file$)
							;~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~;
							;	End of EightBitDragon code	;
							;~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~;
								
							Case "object"
								; Find out wich kind of object is is and act consecuently
								Select xmlNodeAttributeValueGet(SceneChildNode, "type")
										
										
										
									Case "player"
										; Update progress bar
										Game_Stage_UpdateProgressBar("Loading object n."+j+": Player And Camera", Float#(j)/Float#(xmlNodeChildCount(RootChildNode)))
										
										
										; Load Model
										cType = CharType
										
										If (UseStageChar <> 0) Then
										SceneType = xmlNodeFind("character", SceneChildNode)
										If (SceneType <> 0) Then
											cType = xmlNodeAttributeValueGet(SceneType, "type")
											ModelSelect$ = xmlNodeAttributeValueGet(SceneType, "model")
										EndIf
										
									EndIf
										
									p.tPlayer = Player_Create(cType)
										DebugLog("Character Type: " + cType)
										
										If (Game\CreatedCharacter = 0) Then
											
											For	c.tCamera= Each tCamera
												Camera_Bind(c, p)
											Next
											
										EndIf
										
										
										If (Game\CreatedCharacter = 0) Then
										For	c.tCamera= Each tCamera
											c\Listener = CreateListener(c\Entity, 0.006, 0.1, 1)
											ListenerCreated = 1
										Next
									EndIf
										
										Game\CreatedCharacter = 1
										
									
										; Setup position, rotation and scale.
										ScenePosition = xmlNodeFind("position", SceneChildNode)
									;	If (ScenePosition<>0) Then PositionEntity(p\Objects\Entity, Float(xmlNodeAttributeValueGet(ScenePosition, "x")), Float(xmlNodeAttributeValueGet(ScenePosition, "y")), Float(xmlNodeAttributeValueGet(ScenePosition, "z")))
										
										Game\Stage\DefStartX# = Float(xmlNodeAttributeValueGet(ScenePosition, "x"))
										Game\Stage\DefStartY# = Float(xmlNodeAttributeValueGet(ScenePosition, "y"))
										Game\Stage\DefStartZ# = Float(xmlNodeAttributeValueGet(ScenePosition, "z"))
										
										Game\Stage\startaction$ = ACTION_COMMON
										
								;		Game\Stage\startaction$ = (xmlNodeAttributeValueGet(SceneAction$, "action"))
										
									Case "playertag"
										; Update progress bar
										Game_Stage_UpdateProgressBar("Loading object n."+j+": Tag Player", Float#(j)/Float#(xmlNodeChildCount(RootChildNode)))
										
										
										
																				; Setup position, rotation and scale.
										
										
									;	Game\Stage\DefStartX# = Float(xmlNodeAttributeValueGet(ScenePosition, "x"))
									;	Game\Stage\DefStartY# = Float(xmlNodeAttributeValueGet(ScenePosition, "y"))
									;	Game\Stage\DefStartZ# = Float(xmlNodeAttributeValueGet(ScenePosition, "z"))
										
										cpath$ = "Sonic"
										
										ScenePath = xmlNodeFind("character", SceneChildNode)
										If (ScenePath<>0) Then cpath$ = xmlNodeAttributeValueGet(ScenePath, "path")
										
										char = 1
										SceneType = xmlNodeFind("follow", SceneChildNode)
										If (SceneType<>0) Then char = xmlNodeAttributeValueGet(SceneType, "type")
										
										p2.tPlayer2 = Player_Create2(char, cpath$)
										
										ScenePosition = xmlNodeFind("position", SceneChildNode)
										If (ScenePosition<>0) Then PositionEntity(p2\Objects\Entity2, Float(xmlNodeAttributeValueGet(ScenePosition, "x")), Float(xmlNodeAttributeValueGet(ScenePosition, "y")), Float(xmlNodeAttributeValueGet(ScenePosition, "z")))
										
									Case "sidekick"
										; Update progress bar
										Game_Stage_UpdateProgressBar("Loading object n."+j+": Tag Player", Float#(j)/Float#(xmlNodeChildCount(RootChildNode)))
										
										
										
																				; Setup position, rotation and scale.
										
										
									;	Game\Stage\DefStartX# = Float(xmlNodeAttributeValueGet(ScenePosition, "x"))
									;	Game\Stage\DefStartY# = Float(xmlNodeAttributeValueGet(ScenePosition, "y"))
									;	Game\Stage\DefStartZ# = Float(xmlNodeAttributeValueGet(ScenePosition, "z"))
										
										cpath$ = "Sonic"
										
										ScenePath = xmlNodeFind("character", SceneChildNode)
										If (ScenePath<>0) Then cpath$ = xmlNodeAttributeValueGet(ScenePath, "path")
										
										char = 1
										SceneType = xmlNodeFind("follow", SceneChildNode)
										If (SceneType<>0) Then char = xmlNodeAttributeValueGet(SceneType, "type")
										
										p2.tPlayer2 = Player_Create2(char, cpath$)
										
										ScenePosition = xmlNodeFind("position", SceneChildNode)
										If (ScenePosition<>0) Then PositionEntity(p2\Objects\Entity2, Float(xmlNodeAttributeValueGet(ScenePosition, "x")), Float(xmlNodeAttributeValueGet(ScenePosition, "y")), Float(xmlNodeAttributeValueGet(ScenePosition, "z")))
										
										
									Case "ring"
										; Update progress bar
										Game_Stage_UpdateProgressBar("Loading object n."+j+": Ring", Float#(j)/Float#(xmlNodeChildCount(RootChildNode)))
										Object_Ring_Parse(SceneChildNode)
										
									Case "lodring"
										; Update progress bar
										Game_Stage_UpdateProgressBar("Loading object n."+j+": Ring", Float#(j)/Float#(xmlNodeChildCount(RootChildNode)))
										Object_Ring_Parse(SceneChildNode)
										
										
									Case "spewring"
										; Update progress bar
										Game_Stage_UpdateProgressBar("Loading object n."+j+": Spew Ring", Float#(j)/Float#(xmlNodeChildCount(RootChildNode)))
										
										positionX# = 0
										positionY# = 0
										positionZ# = 0
										
										; Setup position, rotation and scale.
										ScenePosition = xmlNodeFind("position", SceneChildNode)
										If (ScenePosition<>0) Then
											positionX# = Float(xmlNodeAttributeValueGet(ScenePosition, "x"))
											positionY# = Float(xmlNodeAttributeValueGet(ScenePosition, "y"))
											positionZ# = Float(xmlNodeAttributeValueGet(ScenePosition, "z"))
										End If
										
										obj.tObject = Object_SpewRing_Create(positionX#, positionY#, positionZ#)
										
									Case "spring"
										; Update progress bar
										Game_Stage_UpdateProgressBar("Loading object n."+j+": Spring", Float#(j)/Float#(xmlNodeChildCount(RootChildNode)))
										
										positionX# = 0
										positionY# = 0
										positionZ# = 0
										
										rotationX# = 0
										rotationY# = 0
										rotationZ# = 0
										
										power# = 3
										
										; Setup position, rotation and scale.
										ScenePosition = xmlNodeFind("position", SceneChildNode)
										If (ScenePosition<>0) Then
											positionX# = Float(xmlNodeAttributeValueGet(ScenePosition, "x"))
											positionY# = Float(xmlNodeAttributeValueGet(ScenePosition, "y"))
											positionZ# = Float(xmlNodeAttributeValueGet(ScenePosition, "z"))
										End If
										
										SceneRotation = xmlNodeFind("rotation", SceneChildNode)
										If (SceneRotation<>0) Then										
											rotationX# = Float(xmlNodeAttributeValueGet(SceneRotation, "pitch"))
											rotationY# = Float(xmlNodeAttributeValueGet(SceneRotation, "yaw"))
											rotationZ# = Float(xmlNodeAttributeValueGet(SceneRotation, "roll"))
										End If
										
										ScenePower = xmlNodeFind("power", SceneChildNode)
										If (ScenePower<>0) Then										
											power# = Float(xmlNodeAttributeValueGet(ScenePower, "is"))
										EndIf
										
											obj.tObject = Object_Spring_Create(positionX#, positionY#, positionZ#, rotationX#, rotationY#, rotationZ#, power#)
										
										
									Case "spring2"
										; Update progress bar
										Game_Stage_UpdateProgressBar("Loading object n."+j+": Spring", Float#(j)/Float#(xmlNodeChildCount(RootChildNode)))
										
										positionX# = 0
										positionY# = 0
										positionZ# = 0
										
										rotationX# = 0
										rotationY# = 0
										rotationZ# = 0
										
										; Setup position, rotation and scale.
										ScenePosition = xmlNodeFind("position", SceneChildNode)
										If (ScenePosition<>0) Then
											positionX# = Float(xmlNodeAttributeValueGet(ScenePosition, "x"))
											positionY# = Float(xmlNodeAttributeValueGet(ScenePosition, "y"))
											positionZ# = Float(xmlNodeAttributeValueGet(ScenePosition, "z"))
										End If
										
										SceneRotation = xmlNodeFind("rotation", SceneChildNode)
										If (SceneRotation<>0) Then										
											rotationX# = Float(xmlNodeAttributeValueGet(SceneRotation, "pitch"))
											rotationY# = Float(xmlNodeAttributeValueGet(SceneRotation, "yaw"))
											rotationZ# = Float(xmlNodeAttributeValueGet(SceneRotation, "roll"))
										End If
										
										obj.tObject = Object_Spring2_Create(positionX#, positionY#, positionZ#, rotationX#, rotationY#, rotationZ#)
										
									Case "spring3"
										; Update progress bar
										Game_Stage_UpdateProgressBar("Loading object n."+j+": Spring", Float#(j)/Float#(xmlNodeChildCount(RootChildNode)))
										
										positionX# = 0
										positionY# = 0
										positionZ# = 0
										
										rotationX# = 0
										rotationY# = 0
										rotationZ# = 0
										
										; Setup position, rotation and scale.
										ScenePosition = xmlNodeFind("position", SceneChildNode)
										If (ScenePosition<>0) Then
											positionX# = Float(xmlNodeAttributeValueGet(ScenePosition, "x"))
											positionY# = Float(xmlNodeAttributeValueGet(ScenePosition, "y"))
											positionZ# = Float(xmlNodeAttributeValueGet(ScenePosition, "z"))
										End If
										
										SceneRotation = xmlNodeFind("rotation", SceneChildNode)
										If (SceneRotation<>0) Then										
											rotationX# = Float(xmlNodeAttributeValueGet(SceneRotation, "pitch"))
											rotationY# = Float(xmlNodeAttributeValueGet(SceneRotation, "yaw"))
											rotationZ# = Float(xmlNodeAttributeValueGet(SceneRotation, "roll"))
										End If
										
										obj.tObject = Object_Spring3_Create(positionX#, positionY#, positionZ#, rotationX#, rotationY#, rotationZ#)
										
									Case "spring4"
										; Update progress bar
										Game_Stage_UpdateProgressBar("Loading object n."+j+": Spring", Float#(j)/Float#(xmlNodeChildCount(RootChildNode)))
										
										positionX# = 0
										positionY# = 0
										positionZ# = 0
										
										rotationX# = 0
										rotationY# = 0
										rotationZ# = 0
										
										; Setup position, rotation and scale.
										ScenePosition = xmlNodeFind("position", SceneChildNode)
										If (ScenePosition<>0) Then
											positionX# = Float(xmlNodeAttributeValueGet(ScenePosition, "x"))
											positionY# = Float(xmlNodeAttributeValueGet(ScenePosition, "y"))
											positionZ# = Float(xmlNodeAttributeValueGet(ScenePosition, "z"))
										End If
										
										SceneRotation = xmlNodeFind("rotation", SceneChildNode)
										If (SceneRotation<>0) Then										
											rotationX# = Float(xmlNodeAttributeValueGet(SceneRotation, "pitch"))
											rotationY# = Float(xmlNodeAttributeValueGet(SceneRotation, "yaw"))
											rotationZ# = Float(xmlNodeAttributeValueGet(SceneRotation, "roll"))
										End If
										
										obj.tObject = Object_Spring4_Create(positionX#, positionY#, positionZ#, rotationX#, rotationY#, rotationZ#)
										
									Case "monitor"
										; Update progress bar
										Game_Stage_UpdateProgressBar("Loading object n."+j+": Monitor", Float#(j)/Float#(xmlNodeChildCount(RootChildNode)))
										
										positionX# = 0
										positionY# = 0
										positionZ# = 0
										
										; Setup position, rotation and scale.
										ScenePosition = xmlNodeFind("position", SceneChildNode)
										If (ScenePosition<>0) Then
											positionX# = Float(xmlNodeAttributeValueGet(ScenePosition, "x"))
											positionY# = Float(xmlNodeAttributeValueGet(ScenePosition, "y"))
											positionZ# = Float(xmlNodeAttributeValueGet(ScenePosition, "z"))
										End If
										
										
										obj.tObject = Object_Monitor_Create(positionX#, positionY#, positionZ#)
									
									Case "bumper"
										; Update progress bar
										Game_Stage_UpdateProgressBar("Loading object n."+j+": Bumper", Float#(j)/Float#(xmlNodeChildCount(RootChildNode)))
										
										positionX# = 0
										positionY# = 0
										positionZ# = 0
										
										; Setup position, rotation and scale.
										ScenePosition = xmlNodeFind("position", SceneChildNode)
										If (ScenePosition<>0) Then
											positionX# = Float(xmlNodeAttributeValueGet(ScenePosition, "x"))
											positionY# = Float(xmlNodeAttributeValueGet(ScenePosition, "y"))
											positionZ# = Float(xmlNodeAttributeValueGet(ScenePosition, "z"))
										End If
																				
										obj.tObject = Object_Bumper_Create(positionX#, positionY#, positionZ#)
										
									Case "goal"
										; Update progress bar
										Game_Stage_UpdateProgressBar("Loading object n."+j+": Goal", Float#(j)/Float#(xmlNodeChildCount(RootChildNode)))
										
										positionX# = 0
										positionY# = 0
										positionZ# = 0
										
										; Setup position, rotation and scale.
										ScenePosition = xmlNodeFind("position", SceneChildNode)
										If (ScenePosition<>0) Then
											positionX# = Float(xmlNodeAttributeValueGet(ScenePosition, "x"))
											positionY# = Float(xmlNodeAttributeValueGet(ScenePosition, "y"))
											positionZ# = Float(xmlNodeAttributeValueGet(ScenePosition, "z"))
										End If
										
										Destination = xmlNodeFind("destination", SceneChildNode)
										
										SceneRadius = xmlNodeFind("goal", SceneChildNode)
										If (SceneRadius<>0) Then
											radius# = Float(xmlNodeAttributeValueGet(SceneRadius, "radius"))
										EndIf
										
										Ends = 1
										
										If (Destination<>0) Then
											goaldestination$ = xmlNodeAttributeValueGet(Destination, "stage")
											Ends = 0
										EndIf
										
										
										SceneEnds = xmlNodeFind("stage", SceneChildNode)
										If (SceneEnds<>0) Then
											ends = xmlNodeAttributeValueGet(SceneEnds, "ends")
										EndIf
										
										SceneStarting = xmlNodeFind("startposition", SceneChildNode)
										For p.tPlayer = Each tPlayer
											If (SceneStarting<>0) Then
												
										;	p\StartPos = 1
												
												spositionX# = Float(xmlNodeAttributeValueGet(SceneStarting, "x"))
												spositionY# = Float(xmlNodeAttributeValueGet(SceneStarting, "y"))
												spositionZ# = Float(xmlNodeAttributeValueGet(SceneStarting, "z"))
												
												
											End If
											
								;		If (SceneStarting=0) Then
								;			
								;		;	p\StartPos = 1
								;			
								;			spositionX# = p\DefStartX#
								;			spositionY# = p\DefStartX#
								;			spositionZ# = p\DefStartX#
								;			
								;			
								;		End If
										Next
											
																				
										obj.tObject = Object_Goal_Create(positionX#, positionY#, positionZ#, goaldestination$, radius#, ends, spositionX#, spositionY#, spositionZ#)
										
									Case "goalinvis"
										; Update progress bar
										Game_Stage_UpdateProgressBar("Loading object n."+j+": Invisible Goal", Float#(j)/Float#(xmlNodeChildCount(RootChildNode)))
										
										positionX# = 0
										positionY# = 0
										positionZ# = 0
										
										; Setup position, rotation and scale.
										ScenePosition = xmlNodeFind("position", SceneChildNode)
										If (ScenePosition<>0) Then
											positionX# = Float(xmlNodeAttributeValueGet(ScenePosition, "x"))
											positionY# = Float(xmlNodeAttributeValueGet(ScenePosition, "y"))
											positionZ# = Float(xmlNodeAttributeValueGet(ScenePosition, "z"))
										End If
										
										Destination = xmlNodeFind("destination", SceneChildNode)
										
										If (Destination<>0) Then
											goaldestination$ = xmlNodeAttributeValueGet(Destination, "stage")
										EndIf
										
										SceneRadius = xmlNodeFind("goal", SceneChildNode)
										If (SceneRadius<>0) Then
											radius# = Float(xmlNodeAttributeValueGet(SceneRadius, "radius"))
										EndIf
										
										ends = 0
										
										SceneEnds = xmlNodeFind("stage", SceneChildNode)
										If (SceneEnds<>0) Then
											ends = xmlNodeAttributeValueGet(SceneEnds, "ends")
										EndIf
										
										SceneStarting = xmlNodeFind("startposition", SceneChildNode)
										For p.tPlayer = Each tPlayer
										If (SceneStarting<>0) Then
											
										;	p\StartPos = 1
											
											spositionX# = Float(xmlNodeAttributeValueGet(SceneStarting, "x"))
											spositionY# = Float(xmlNodeAttributeValueGet(SceneStarting, "y"))
											spositionZ# = Float(xmlNodeAttributeValueGet(SceneStarting, "z"))
											
											
										End If
										
										usepos=1
										
										SceneUsePos = xmlNodeFind("use", SceneChildNode)
										If (SceneUsePos<>0) Then
											usepos = xmlNodeAttributeValueGet(SceneUsePos, "position")
										EndIf
										
								;		If (SceneStarting=0) Then
								;			
								;		;	p\StartPos = 1
								;			
								;			spositionX# = p\DefStartX#
								;			spositionY# = p\DefStartX#
								;			spositionZ# = p\DefStartX#
								;			
								;			
								;		End If
									Next
											
																				
									obj.tObject = Object_GoalInvis_Create(positionX#, positionY#, positionZ#, goaldestination$, radius#, ends, spositionX#, spositionY#, spositionZ#, usepos)
										
									Case "camlock"
										; Update progress bar
										Game_Stage_UpdateProgressBar("Loading object n."+j+": Camlock", Float#(j)/Float#(xmlNodeChildCount(RootChildNode)))
										
										positionX# = 0
										positionY# = 0
										positionZ# = 0
										
										ctype = 1
										
										; Setup position, rotation and scale.
										ScenePosition = xmlNodeFind("position", SceneChildNode)
										If (ScenePosition<>0) Then
											positionX# = Float(xmlNodeAttributeValueGet(ScenePosition, "x"))
											positionY# = Float(xmlNodeAttributeValueGet(ScenePosition, "y"))
											positionZ# = Float(xmlNodeAttributeValueGet(ScenePosition, "z"))
										End If
										
										SceneRotation = xmlNodeFind("rotation", SceneChildNode)
										If (SceneRotation<>0) Then										
											rotationX# = Float(xmlNodeAttributeValueGet(SceneRotation, "pitch"))
											rotationY# = Float(xmlNodeAttributeValueGet(SceneRotation, "yaw"))
											rotationZ# = Float(xmlNodeAttributeValueGet(SceneRotation, "roll"))
										End If
										
										SceneRadius = xmlNodeFind("camera", SceneChildNode)
										If (SceneRadius<>0) Then										
											radius# = Float(xmlNodeAttributeValueGet(SceneRadius, "radius"))
											ctype	= xmlNodeAttributeValueGet(SceneRadius, "type")
										End If
										
										obj.tObject = Object_CamLock_Create(positionX#, positionY#, positionZ#, rotationX#, rotationY#, rotationZ#, radius#, ctype)
										
									Case "homingnode"
										; Update progress bar
										Game_Stage_UpdateProgressBar("Loading object n."+j+": Homing Node", Float#(j)/Float#(xmlNodeChildCount(RootChildNode)))
										
										positionX# = 0
										positionY# = 0
										positionZ# = 0
										
										rotationX# = 0
										rotationY# = 0
										rotationZ# = 0
										
										; Setup position, rotation and scale.
										ScenePosition = xmlNodeFind("position", SceneChildNode)
										If (ScenePosition<>0) Then
											positionX# = Float(xmlNodeAttributeValueGet(ScenePosition, "x"))
											positionY# = Float(xmlNodeAttributeValueGet(ScenePosition, "y"))
											positionZ# = Float(xmlNodeAttributeValueGet(ScenePosition, "z"))
										End If
										
										SceneRadius = xmlNodeFind("node", SceneChildNode)
										If (SceneRadius<>0) Then
											radius# = Float(xmlNodeAttributeValueGet(SceneRadius, "radius"))
										EndIf
										
										obj.tObject = Object_HomingNode_Create(positionX#, positionY#, positionZ#, radius#)
								
							Case "bomber"
										; Update progress bar
								Game_Stage_UpdateProgressBar("Loading object n."+j+": Bomber Bot", Float#(j)/Float#(xmlNodeChildCount(RootChildNode)))
								
								positionX# = 0
								positionY# = 0
								positionZ# = 0
								
								rotationX# = 0
								rotationY# = 0
								rotationZ# = 0
								
										; Setup position, rotation and scale.
								ScenePosition = xmlNodeFind("position", SceneChildNode)
								If (ScenePosition<>0) Then
									positionX# = Float(xmlNodeAttributeValueGet(ScenePosition, "x"))
									positionY# = Float(xmlNodeAttributeValueGet(ScenePosition, "y"))
									positionZ# = Float(xmlNodeAttributeValueGet(ScenePosition, "z"))
								End If
								
								obj.tObject = Object_Bomber_Create(positionX#, positionY#, positionZ#)
								
							Case "trickhoop"
										; Update progress bar
								Game_Stage_UpdateProgressBar("Loading object n."+j+": Trick Hoop", Float#(j)/Float#(xmlNodeChildCount(RootChildNode)))
								
								positionX# = 0
								positionY# = 0
								positionZ# = 0
								
								rotationX# = 0
								rotationY# = 0
								rotationZ# = 0
								
										; Setup position, rotation and scale.
								ScenePosition = xmlNodeFind("position", SceneChildNode)
								If (ScenePosition<>0) Then
									positionX# = Float(xmlNodeAttributeValueGet(ScenePosition, "x"))
									positionY# = Float(xmlNodeAttributeValueGet(ScenePosition, "y"))
									positionZ# = Float(xmlNodeAttributeValueGet(ScenePosition, "z"))
								End If
								
								SceneRotation = xmlNodeFind("rotation", SceneChildNode)
								If (SceneRotation<>0) Then										
									rotationX# = Float(xmlNodeAttributeValueGet(SceneRotation, "pitch"))
									rotationY# = Float(xmlNodeAttributeValueGet(SceneRotation, "yaw"))
									rotationZ# = Float(xmlNodeAttributeValueGet(SceneRotation, "roll"))
								End If
								
								obj.tObject = Object_TrickHoop_Create(positionX#, positionY#, positionZ#, rotationX#, rotationY#, rotationZ#)
								
							Case "hoop"
										; Update progress bar
								Game_Stage_UpdateProgressBar("Loading object n."+j+": Hoop", Float#(j)/Float#(xmlNodeChildCount(RootChildNode)))
								
								positionX# = 0
								positionY# = 0
								positionZ# = 0
								
								rotationX# = 0
								rotationY# = 0
								rotationZ# = 0
								
										; Setup position, rotation and scale.
								ScenePosition = xmlNodeFind("position", SceneChildNode)
								If (ScenePosition<>0) Then
									positionX# = Float(xmlNodeAttributeValueGet(ScenePosition, "x"))
									positionY# = Float(xmlNodeAttributeValueGet(ScenePosition, "y"))
									positionZ# = Float(xmlNodeAttributeValueGet(ScenePosition, "z"))
								End If
								
								SceneRotation = xmlNodeFind("rotation", SceneChildNode)
								If (SceneRotation<>0) Then										
									rotationX# = Float(xmlNodeAttributeValueGet(SceneRotation, "pitch"))
									rotationY# = Float(xmlNodeAttributeValueGet(SceneRotation, "yaw"))
									rotationZ# = Float(xmlNodeAttributeValueGet(SceneRotation, "roll"))
								End If
								
								obj.tObject = Object_Hoop_Create(positionX#, positionY#, positionZ#, rotationX#, rotationY#, rotationZ#)
								
							Case "flyenemy"
										; Update progress bar
								Game_Stage_UpdateProgressBar("Loading object n."+j+": Flying Enemy", Float#(j)/Float#(xmlNodeChildCount(RootChildNode)))
								
								positionX# = 0
								positionY# = 0
								positionZ# = 0
								
								rotationX# = 0
								rotationY# = 0
								rotationZ# = 0
								
										; Setup position, rotation and scale.
								ScenePosition = xmlNodeFind("position", SceneChildNode)
								If (ScenePosition<>0) Then
									positionX# = Float(xmlNodeAttributeValueGet(ScenePosition, "x"))
									positionY# = Float(xmlNodeAttributeValueGet(ScenePosition, "y"))
									positionZ# = Float(xmlNodeAttributeValueGet(ScenePosition, "z"))
								End If
								
								SceneRotation = xmlNodeFind("rotation", SceneChildNode)
								If (SceneRotation<>0) Then										
									rotationX# = Float(xmlNodeAttributeValueGet(SceneRotation, "pitch"))
									rotationY# = Float(xmlNodeAttributeValueGet(SceneRotation, "yaw"))
									rotationZ# = Float(xmlNodeAttributeValueGet(SceneRotation, "roll"))
								End If
								
								obj.tObject = Object_FlyEnemy_Create(positionX#, positionY#, positionZ#, rotationX#, rotationY#, rotationZ#)
								
							Case "enemy"
										; Update progress bar
								Game_Stage_UpdateProgressBar("Loading object n."+j+": Ground Enemy", Float#(j)/Float#(xmlNodeChildCount(RootChildNode)))
								
								positionX# = 0
								positionY# = 0
								positionZ# = 0
								
								rotationX# = 0
								rotationY# = 0
								rotationZ# = 0
								
										; Setup position, rotation and scale.
								ScenePosition = xmlNodeFind("position", SceneChildNode)
								If (ScenePosition<>0) Then
									positionX# = Float(xmlNodeAttributeValueGet(ScenePosition, "x"))
									positionY# = Float(xmlNodeAttributeValueGet(ScenePosition, "y"))
									positionZ# = Float(xmlNodeAttributeValueGet(ScenePosition, "z"))
								End If
								
								SceneRotation = xmlNodeFind("rotation", SceneChildNode)
								If (SceneRotation<>0) Then										
									rotationX# = Float(xmlNodeAttributeValueGet(SceneRotation, "pitch"))
									rotationY# = Float(xmlNodeAttributeValueGet(SceneRotation, "yaw"))
									rotationZ# = Float(xmlNodeAttributeValueGet(SceneRotation, "roll"))
								End If
								
								obj.tObject = Object_Enemy_Create(positionX#, positionY#, positionZ#, rotationX#, rotationY#, rotationZ#)
								
							Case "robot"
										; Update progress bar
								Game_Stage_UpdateProgressBar("Loading object n."+j+": Ground Enemy", Float#(j)/Float#(xmlNodeChildCount(RootChildNode)))
								
								positionX# = 0
								positionY# = 0
								positionZ# = 0
								
								rotationX# = 0
								rotationY# = 0
								rotationZ# = 0
								
										; Setup position, rotation and scale.
								ScenePosition = xmlNodeFind("position", SceneChildNode)
								If (ScenePosition<>0) Then
									positionX# = Float(xmlNodeAttributeValueGet(ScenePosition, "x"))
									positionY# = Float(xmlNodeAttributeValueGet(ScenePosition, "y"))
									positionZ# = Float(xmlNodeAttributeValueGet(ScenePosition, "z"))
								End If
								
								SceneRotation = xmlNodeFind("rotation", SceneChildNode)
								If (SceneRotation<>0) Then										
									rotationX# = Float(xmlNodeAttributeValueGet(SceneRotation, "pitch"))
									rotationY# = Float(xmlNodeAttributeValueGet(SceneRotation, "yaw"))
									rotationZ# = Float(xmlNodeAttributeValueGet(SceneRotation, "roll"))
								End If
								
								obj.tObject = Object_Enemy_Create(positionX#, positionY#, positionZ#, rotationX#, rotationY#, rotationZ#)
								
								
							Case "check"
										; Update progress bar
								Game_Stage_UpdateProgressBar("Loading object n."+j+": Checkpoint", Float#(j)/Float#(xmlNodeChildCount(RootChildNode)))
								
								positionX# = 0
								positionY# = 0
								positionZ# = 0
								
								rotationX# = 0
								rotationY# = 0
								rotationZ# = 0
								
										; Setup position, rotation and scale.
								ScenePosition = xmlNodeFind("position", SceneChildNode)
								If (ScenePosition<>0) Then
									positionX# = Float(xmlNodeAttributeValueGet(ScenePosition, "x"))
									positionY# = Float(xmlNodeAttributeValueGet(ScenePosition, "y"))
									positionZ# = Float(xmlNodeAttributeValueGet(ScenePosition, "z"))
								End If
								
								SceneRotation = xmlNodeFind("rotation", SceneChildNode)
								If (SceneRotation<>0) Then										
									rotationX# = Float(xmlNodeAttributeValueGet(SceneRotation, "pitch"))
									rotationY# = Float(xmlNodeAttributeValueGet(SceneRotation, "yaw"))
									rotationZ# = Float(xmlNodeAttributeValueGet(SceneRotation, "roll"))
								End If
								
								obj.tObject = Object_Check_Create(positionX#, positionY#, positionZ#, rotationX#, rotationY#, rotationZ#)
								
							Case "checkpoint"
										; Update progress bar
								Game_Stage_UpdateProgressBar("Loading object n."+j+": Checkpoint", Float#(j)/Float#(xmlNodeChildCount(RootChildNode)))
								
								positionX# = 0
								positionY# = 0
								positionZ# = 0
								
								rotationX# = 0
								rotationY# = 0
								rotationZ# = 0
								
										; Setup position, rotation and scale.
								ScenePosition = xmlNodeFind("position", SceneChildNode)
								If (ScenePosition<>0) Then
									positionX# = Float(xmlNodeAttributeValueGet(ScenePosition, "x"))
									positionY# = Float(xmlNodeAttributeValueGet(ScenePosition, "y"))
									positionZ# = Float(xmlNodeAttributeValueGet(ScenePosition, "z"))
								End If
								
								SceneRotation = xmlNodeFind("rotation", SceneChildNode)
								If (SceneRotation<>0) Then										
									rotationX# = Float(xmlNodeAttributeValueGet(SceneRotation, "pitch"))
									rotationY# = Float(xmlNodeAttributeValueGet(SceneRotation, "yaw"))
									rotationZ# = Float(xmlNodeAttributeValueGet(SceneRotation, "roll"))
								End If
								
								obj.tObject = Object_Check_Create(positionX#, positionY#, positionZ#, rotationX#, rotationY#, rotationZ#)
								
							Case "dashpad"
										; Update progress bar
								Game_Stage_UpdateProgressBar("Loading object n."+j+": Dash Pad", Float#(j)/Float#(xmlNodeChildCount(RootChildNode)))
								
								positionX# = 0
								positionY# = 0
								positionZ# = 0
								
								rotationX# = 0
								rotationY# = 0
								rotationZ# = 0
								
								pwr# = 4.8
								cam# = 1
								
										; Setup position, rotation and scale.
								ScenePosition = xmlNodeFind("position", SceneChildNode)
								If (ScenePosition<>0) Then
									positionX# = Float(xmlNodeAttributeValueGet(ScenePosition, "x"))
									positionY# = Float(xmlNodeAttributeValueGet(ScenePosition, "y"))
									positionZ# = Float(xmlNodeAttributeValueGet(ScenePosition, "z"))
								End If
								
								SceneRotation = xmlNodeFind("rotation", SceneChildNode)
								If (SceneRotation<>0) Then										
									rotationX# = Float(xmlNodeAttributeValueGet(SceneRotation, "pitch"))
									rotationY# = Float(xmlNodeAttributeValueGet(SceneRotation, "yaw"))
									rotationZ# = Float(xmlNodeAttributeValueGet(SceneRotation, "roll"))
								End If
								
								ScenePower = xmlNodeFind("power", SceneChildNode)
								If (ScenePower<>0) Then										
									pwr# = Float(xmlNodeAttributeValueGet(ScenePower, "is"))
								End If	
								
								SceneCamera = xmlNodeFind("cam", SceneChildNode)
								If (SceneCamera<>0) Then										
									cam = Int(xmlNodeAttributeValueGet(SceneCamera, "on"))
								End If	
								
								obj.tObject = Object_DashPad_Create(positionX#, positionY#, positionZ#, rotationX#, rotationY#, rotationZ#, pwr#, cam)
								
							Case "npc"
										; Update progress bar
								Game_Stage_UpdateProgressBar("Loading object n."+j+": Non-Playable Character - NPC", Float#(j)/Float#(xmlNodeChildCount(RootChildNode)))
								
								positionX# = 0
								positionY# = 0
								positionZ# = 0
								
								rotationX# = 0
								rotationY# = 0
								rotationZ# = 0
								
										; Setup position, rotation and scale.
								ScenePosition = xmlNodeFind("position", SceneChildNode)
								If (ScenePosition<>0) Then
									positionX# = Float(xmlNodeAttributeValueGet(ScenePosition, "x"))
									positionY# = Float(xmlNodeAttributeValueGet(ScenePosition, "y"))
									positionZ# = Float(xmlNodeAttributeValueGet(ScenePosition, "z"))
								End If
								
								SceneRotation = xmlNodeFind("rotation", SceneChildNode)
								If (SceneRotation<>0) Then										
									rotationX# = Float(xmlNodeAttributeValueGet(SceneRotation, "pitch"))
									rotationY# = Float(xmlNodeAttributeValueGet(SceneRotation, "yaw"))
									rotationZ# = Float(xmlNodeAttributeValueGet(SceneRotation, "roll"))
								End If
								
								SceneSpeech = xmlNodeFind("npc", SceneChildNode)
								If (SceneSpeech<>0) Then										
									Speech$ = xmlNodeAttributeValueGet(SceneSpeech, "text")
								End If	
								
								Animated = 0
							;	Radius# = 9
								
								SceneModel = xmlNodeFind("object", SceneChildNode)
								If (SceneModel<>0) Then										
									Model$ = xmlNodeAttributeValueGet(SceneModel, "model")
									Animated = xmlNodeAttributeValueGet(SceneModel, "animated")
								End If
								
								SceneSound = xmlNodeFind("play", SceneChildNode)
								If (SceneSound<>0) Then										
									Sound$ = xmlNodeAttributeValueGet(SceneSound, "sound")
								End If
								
								SceneRadius = xmlNodeFind("radius", SceneChildNode)
								If (SceneRadius<>0) Then										
									Radius# = xmlNodeAttributeValueGet(SceneRadius, "amount")
								End If
								
								obj.tObject = Object_NPC_Create(positionX#, positionY#, positionZ#, rotationX#, rotationY#, rotationZ#, speech$, model$, sound$, animated, radius#)
								
							Case "dashpadnew"
										; Update progress bar
								Game_Stage_UpdateProgressBar("Loading object n."+j+": Re-Coded Dash Pad", Float#(j)/Float#(xmlNodeChildCount(RootChildNode)))
								
								positionX# = 0
								positionY# = 0
								positionZ# = 0
								
								rotationX# = 0
								rotationY# = 0
								rotationZ# = 0
								
								pwr# = 4.8
								cam# = 1
								
										; Setup position, rotation and scale.
								ScenePosition = xmlNodeFind("position", SceneChildNode)
								If (ScenePosition<>0) Then
									positionX# = Float(xmlNodeAttributeValueGet(ScenePosition, "x"))
									positionY# = Float(xmlNodeAttributeValueGet(ScenePosition, "y"))
									positionZ# = Float(xmlNodeAttributeValueGet(ScenePosition, "z"))
								End If
								
								SceneRotation = xmlNodeFind("rotation", SceneChildNode)
								If (SceneRotation<>0) Then										
									rotationX# = Float(xmlNodeAttributeValueGet(SceneRotation, "pitch"))
									rotationY# = Float(xmlNodeAttributeValueGet(SceneRotation, "yaw"))
									rotationZ# = Float(xmlNodeAttributeValueGet(SceneRotation, "roll"))
								End If
								
								ScenePower = xmlNodeFind("power", SceneChildNode)
								If (ScenePower<>0) Then										
									pwr# = Float(xmlNodeAttributeValueGet(ScenePower, "is"))
								End If	
								
								SceneCamera = xmlNodeFind("cam", SceneChildNode)
								If (SceneCamera<>0) Then										
									cam = Int(xmlNodeAttributeValueGet(SceneCamera, "on"))
								End If	
								
								obj.tObject = Object_DashPadNew_Create(positionX#, positionY#, positionZ#, rotationX#, rotationY#, rotationZ#, pwr#, cam)
								
							Case "dashramp"
										; Update progress bar
								Game_Stage_UpdateProgressBar("Loading object n."+j+": DashRamp", Float#(j)/Float#(xmlNodeChildCount(RootChildNode)))
								
								positionX# = 0
								positionY# = 0
								positionZ# = 0
								
								rotationX# = 0
								rotationY# = 0
								rotationZ# = 0
								
										; Setup position, rotation and scale.
								ScenePosition = xmlNodeFind("position", SceneChildNode)
								If (ScenePosition<>0) Then
									positionX# = Float(xmlNodeAttributeValueGet(ScenePosition, "x"))
									positionY# = Float(xmlNodeAttributeValueGet(ScenePosition, "y"))
									positionZ# = Float(xmlNodeAttributeValueGet(ScenePosition, "z"))
								End If
								
								SceneRotation = xmlNodeFind("rotation", SceneChildNode)
								If (SceneRotation<>0) Then										
									rotationX# = Float(xmlNodeAttributeValueGet(SceneRotation, "pitch"))
									rotationY# = Float(xmlNodeAttributeValueGet(SceneRotation, "yaw"))
									rotationZ# = Float(xmlNodeAttributeValueGet(SceneRotation, "roll"))
								End If
								
								obj.tObject = Object_DashRamp_Create(positionX#, positionY#, positionZ#, rotationX#, rotationY#, rotationZ#)
								
							Case "ambsound"
										; Update progress bar
								Game_Stage_UpdateProgressBar("Loading object n."+j+": Ambient Sound", Float#(j)/Float#(xmlNodeChildCount(RootChildNode)))
								
								positionX# = 0
								positionY# = 0
								positionZ# = 0
								
										; Setup position, rotation and scale.
								ScenePosition = xmlNodeFind("position", SceneChildNode)
								If (ScenePosition<>0) Then
									positionX# = Float(xmlNodeAttributeValueGet(ScenePosition, "x"))
									positionY# = Float(xmlNodeAttributeValueGet(ScenePosition, "y"))
									positionZ# = Float(xmlNodeAttributeValueGet(ScenePosition, "z"))
								End If
								
								SceneSound = xmlNodeFind("play", SceneChildNode)
							;	If (SceneSound<>0) Then										
								sound$ = xmlNodeAttributeValueGet(SceneSound, "sound")
							;	End If
								
								SceneVolume = xmlNodeFind("sound", SceneChildNode)
								If (SceneVolume<>0) Then										
									volume# = Float(xmlNodeAttributeValueGet(SceneVolume, "volume"))
								End If
								
								obj.tObject = Object_AmbSound_Create(positionX#, positionY#, positionZ#, sound$, volume#)
								
							Case "balloon"
										; Update progress bar
								Game_Stage_UpdateProgressBar("Loading object n."+j+": Balloon", Float#(j)/Float#(xmlNodeChildCount(RootChildNode)))
								
								positionX# = 0
								positionY# = 0
								positionZ# = 0
								
										; Setup position, rotation and scale.
								ScenePosition = xmlNodeFind("position", SceneChildNode)
								If (ScenePosition<>0) Then
									positionX# = Float(xmlNodeAttributeValueGet(ScenePosition, "x"))
									positionY# = Float(xmlNodeAttributeValueGet(ScenePosition, "y"))
									positionZ# = Float(xmlNodeAttributeValueGet(ScenePosition, "z"))
								End If
								
								obj.tObject = Object_Balloon_Create(positionX#, positionY#, positionZ#)
								
							Case "pickup"
										; Update progress bar
								Game_Stage_UpdateProgressBar("Loading object n."+j+": Holdable Object", Float#(j)/Float#(xmlNodeChildCount(RootChildNode)))
								
								positionX# = 0
								positionY# = 0
								positionZ# = 0
								
										; Setup position, rotation and scale.
								ScenePosition = xmlNodeFind("position", SceneChildNode)
								If (ScenePosition<>0) Then
									positionX# = Float(xmlNodeAttributeValueGet(ScenePosition, "x"))
									positionY# = Float(xmlNodeAttributeValueGet(ScenePosition, "y"))
									positionZ# = Float(xmlNodeAttributeValueGet(ScenePosition, "z"))
								End If
								
								obj.tObject = Object_PickUp_Create(positionX#, positionY#, positionZ#)
								
							Case "destspring"
										; Update progress bar
								Game_Stage_UpdateProgressBar("Loading object n."+j+": Destination Spring", Float#(j)/Float#(xmlNodeChildCount(RootChildNode)))
								
								positionX# = 0
								positionY# = 0
								positionZ# = 0
								
								rotationX# = 0
								rotationY# = 0
								rotationZ# = 0
								
										; Setup position, rotation and scale.
								ScenePosition = xmlNodeFind("position", SceneChildNode)
								If (ScenePosition<>0) Then
									positionX# = Float(xmlNodeAttributeValueGet(ScenePosition, "x"))
									positionY# = Float(xmlNodeAttributeValueGet(ScenePosition, "y"))
									positionZ# = Float(xmlNodeAttributeValueGet(ScenePosition, "z"))
								End If
								
										; Setup position, rotation and scale.
								ScenePosition = xmlNodeFind("destination", SceneChildNode)
								If (ScenePosition<>0) Then
									destX# = Float(xmlNodeAttributeValueGet(ScenePosition, "x"))
									destY# = Float(xmlNodeAttributeValueGet(ScenePosition, "y"))
									destZ# = Float(xmlNodeAttributeValueGet(ScenePosition, "z"))
								End If
								
								SceneRotation = xmlNodeFind("rotation", SceneChildNode)
								If (SceneRotation<>0) Then										
									rotationX# = Float(xmlNodeAttributeValueGet(SceneRotation, "pitch"))
									rotationY# = Float(xmlNodeAttributeValueGet(SceneRotation, "yaw"))
									rotationZ# = Float(xmlNodeAttributeValueGet(SceneRotation, "roll"))
								End If
								
								SceneStops = xmlNodeFind("player", SceneChildNode)
								If (SceneStops<>0) Then
									stops = Float(xmlNodeAttributeValueGet(SceneStops, "stops"))
								EndIf
								
								SceneWJ = xmlNodeFind("spring", SceneChildNode)
								If (SceneWJ<>0) Then
									walljump = Float(xmlNodeAttributeValueGet(SceneWJ, "walljumps"))
								EndIf
								
								SceneSpeed = xmlNodeFind("launch", SceneChildNode)
								If (SceneSpeed<>0) Then
									speed# = Float(xmlNodeAttributeValueGet(SceneSpeed, "speed"))
								EndIf
								
								obj.tObject = Object_DestSpring_Create(positionX#, positionY#, positionZ#, rotationX#, rotationY#, rotationZ#, destX#, destY#, destZ#, stops, speed#, walljump)
								
							Case "hint"
										; Update progress bar
								Game_Stage_UpdateProgressBar("Loading object n."+j+": Hint", Float#(j)/Float#(xmlNodeChildCount(RootChildNode)))
								
								positionX# = 0
								positionY# = 0
								positionZ# = 0
								
								rotationX# = 0
								rotationY# = 0
								rotationZ# = 0
								
										; Setup position, rotation and scale.
								ScenePosition = xmlNodeFind("position", SceneChildNode)
								If (ScenePosition<>0) Then
									positionX# = Float(xmlNodeAttributeValueGet(ScenePosition, "x"))
									positionY# = Float(xmlNodeAttributeValueGet(ScenePosition, "y"))
									positionZ# = Float(xmlNodeAttributeValueGet(ScenePosition, "z"))
								End If
								
								HintInfo = xmlNodeFind("Hint", SceneChildNode)
										;If (HintInfo<>0) Then
								Hint$ = xmlNodeAttributeValueGet(HintInfo, "sound") ;xmlNodeAttributeValueGet(HintInfo, "sound")
										;EndIf
								
								obj.tObject = Object_Hint_Create(positionX#, positionY#, positionZ#, Hint$)
								
							Case "hintinvis"
										; Update progress bar
								Game_Stage_UpdateProgressBar("Loading object n."+j+": Invisible Hint", Float#(j)/Float#(xmlNodeChildCount(RootChildNode)))
								
								positionX# = 0
								positionY# = 0
								positionZ# = 0
								
								rotationX# = 0
								rotationY# = 0
								rotationZ# = 0
								
										; Setup position, rotation and scale.
								ScenePosition = xmlNodeFind("position", SceneChildNode)
								If (ScenePosition<>0) Then
									positionX# = Float(xmlNodeAttributeValueGet(ScenePosition, "x"))
									positionY# = Float(xmlNodeAttributeValueGet(ScenePosition, "y"))
									positionZ# = Float(xmlNodeAttributeValueGet(ScenePosition, "z"))
								End If
								
								
								HintInfo = xmlNodeFind("Hint", SceneChildNode)
								If (HintInfo<>0) Then
								radius# = 90
								radius# = Float(xmlNodeAttributeValueGet(HintInfo, "radius"))
								Hint$ = xmlNodeAttributeValueGet(HintInfo, "sound") ;xmlNodeAttributeValueGet(HintInfo, "sound")
							EndIf
								
								obj.tObject = Object_HintInvis_Create(positionX#, positionY#, positionZ#, Hint$, radius#)
								
							Case "gravity"
										; Update progress bar
								Game_Stage_UpdateProgressBar("Loading object n."+j+": Gravity Affector", Float#(j)/Float#(xmlNodeChildCount(RootChildNode)))
								
								positionX# = 0
								positionY# = 0
								positionZ# = 0
								
								rotationX# = 0
								rotationY# = 0
								rotationZ# = 0
								
										; Setup position, rotation and scale.
								ScenePosition = xmlNodeFind("position", SceneChildNode)
								If (ScenePosition<>0) Then
									positionX# = Float(xmlNodeAttributeValueGet(ScenePosition, "x"))
									positionY# = Float(xmlNodeAttributeValueGet(ScenePosition, "y"))
									positionZ# = Float(xmlNodeAttributeValueGet(ScenePosition, "z"))
								End If
								
								
								SceneGravity = xmlNodeFind("gravity", SceneChildNode)
										;If (HintInfo<>0) Then
								If (SceneGravity<>0) Then
									
									radius# = Float(xmlNodeAttributeValueGet(SceneGravity, "radius"))
									transtime# = Float(xmlNodeAttributeValueGet(SceneGravity, "transitiontime"))
									duration# = Float(xmlNodeAttributeValueGet(SceneGravity, "duration"))
									tX# = Float(xmlNodeAttributeValueGet(SceneGravity, "tiltx"))
									tY# = Float(xmlNodeAttributeValueGet(SceneGravity, "tilty"))
									tZ# = Float(xmlNodeAttributeValueGet(SceneGravity, "tiltz"))
								End If
								
								obj.tObject = Object_Gravity_Create(positionX#, positionY#, positionZ#, transtime#, duration#, tx#, ty#, tz#, radius#)
								
							Case "musictrigger"
										; Update progress bar
								Game_Stage_UpdateProgressBar("Loading object n."+j+": Music Trigger", Float#(j)/Float#(xmlNodeChildCount(RootChildNode)))
								
								positionX# = 0
								positionY# = 0
								positionZ# = 0
								
								rotationX# = 0
								rotationY# = 0
								rotationZ# = 0
								
										; Setup position, rotation and scale.
								ScenePosition = xmlNodeFind("position", SceneChildNode)
								If (ScenePosition<>0) Then
									positionX# = Float(xmlNodeAttributeValueGet(ScenePosition, "x"))
									positionY# = Float(xmlNodeAttributeValueGet(ScenePosition, "y"))
									positionZ# = Float(xmlNodeAttributeValueGet(ScenePosition, "z"))
								End If
								
								
								SceneMusic = xmlNodeFind("trigger", SceneChildNode)
										;If (HintInfo<>0) Then
								If (SceneMusic<>0) Then
									
									radius# = Float(xmlNodeAttributeValueGet(SceneMusic, "radius"))
									music$ = Float(xmlNodeAttributeValueGet(SceneMusic, "music"))
									
								End If
								
								obj.tObject = Object_Music_Create(positionX#, positionY#, positionZ#, music$, radius#)
								
							Case "terminal"
										; Update progress bar
								Game_Stage_UpdateProgressBar("Loading object n."+j+": Terminal", Float#(j)/Float#(xmlNodeChildCount(RootChildNode)))
								
								positionX# = 0
								positionY# = 0
								positionZ# = 0
								
								rotationX# = 0
								rotationY# = 0
								rotationZ# = 0
								
								stageinput = 1
								
										; Setup position, rotation and scale.
								ScenePosition = xmlNodeFind("position", SceneChildNode)
								If (ScenePosition<>0) Then
									positionX# = Float(xmlNodeAttributeValueGet(ScenePosition, "x"))
									positionY# = Float(xmlNodeAttributeValueGet(ScenePosition, "y"))
									positionZ# = Float(xmlNodeAttributeValueGet(ScenePosition, "z"))
								End If
								
								SceneRotation = xmlNodeFind("rotation", SceneChildNode)
								If (SceneRotation<>0) Then										
									rotationX# = Float(xmlNodeAttributeValueGet(SceneRotation, "pitch"))
									rotationY# = Float(xmlNodeAttributeValueGet(SceneRotation, "yaw"))
									rotationZ# = Float(xmlNodeAttributeValueGet(SceneRotation, "roll"))
								End If
								
								SceneMonitor = xmlNodeFind("monitor", SceneChildNode)
								If (SceneMonitor<>0) Then										
									buttons = xmlNodeAttributeValueGet(SceneMonitor, "buttons")
									stageinput = xmlNodeAttributeValueGet(SceneMonitor, "stageinput")
								End If
								
								SceneStages = xmlNodeFind("stage", SceneChildNode)
								If (SceneStages<>0) Then
									stage1$ = xmlNodeAttributeValueGet(SceneStages, "1")
									stage2$ = xmlNodeAttributeValueGet(SceneStages, "2")
									stage3$ = xmlNodeAttributeValueGet(SceneStages, "3")
									stage4$ = xmlNodeAttributeValueGet(SceneStages, "4")
									stage5$ = xmlNodeAttributeValueGet(SceneStages, "5")
									stage6$ = xmlNodeAttributeValueGet(SceneStages, "6")
									stage7$ = xmlNodeAttributeValueGet(SceneStages, "7")
									stage8$ = xmlNodeAttributeValueGet(SceneStages, "8")
									stage9$ = xmlNodeAttributeValueGet(SceneStages, "9")
									stage10$ = xmlNodeAttributeValueGet(SceneStages, "10")
								End If
								
								obj.tObject = Object_Terminal_Create(positionX#, positionY#, positionZ#, rotationX#, rotationY#, rotationZ#, buttons, stageinput, stage1$, stage2$, stage3$, stage4$, stage5$, stage6$, stage7$, stage8$, stage9$, stage10$)
								
							Case "terminal"
								
								; Update progress bar
								Game_Stage_UpdateProgressBar("Loading object n."+j+": Platform", Float#(j)/Float#(xmlNodeChildCount(RootChildNode)))
								
									; Create some local variables.
								positionX# = 0
								positionY# = 0
								positionZ# = 0
								
								; Setup position, rotation and scale.
								ScenePosition = xmlNodeFind("position", SceneChildNode)
								If (ScenePosition<>0) Then
									positionX# = Float(xmlNodeAttributeValueGet(ScenePosition, "x"))
									positionY# = Float(xmlNodeAttributeValueGet(ScenePosition, "y"))
									positionZ# = Float(xmlNodeAttributeValueGet(ScenePosition, "z"))
								End If
								
								; Execute the create object function.
								obj.tObject = Object_Platform_Create(positionX#, positionY#, positionZ#)
								
								
						End Select
				End Select
					Next
	
			End Select 
		Next
		xmlNodeDelete(RootNode)
		
		; ---PARTICLES---
		
		;p\Objects\Emt1 = PW_CreateEmitter()
		;PW_SetEmitter(p\Objects\Emt1, 0, 75, 50, 10, 180, 360)
	;	For c.tCamera = Each tCamera
	;		PW_SetParticle(p\Objects\Emt1, p\Objects\Entity, 1, 2, c\Entity, 0)
	;	Next
	;	PW_SetTexture(p\Objects\Emt1, p\Objects\Dust, 0, 0, 1)
	;	PW_SetVelocity(p\Objects\Emt1, 0.2, 0, 1, 0)
	;	PW_SetAlpha(p\Objects\Emt1, 1, 0, 60)
	;	PW_SetAngle(p\Objects\Emt1, 0, 0, 0, 360)
	;	PW_SetRotation(p\Objects\Emt1, 0, 0, 0, 1)
	;	PW_SetScale(p\Objects\Emt1, 7, 7, 7, 0, 0.9999)
		
	;	If (Game\Stage\Properties\Rain = 1) Then Game\Stage\Rain = CreateTemplate()
		
	;	If (Game\Stage\Properties\Snow = 1) Then Game\Stage\Snow = CreateTemplate()
		
		For c.tCamera = Each tCamera
		
		If (Game\Stage\Properties\Rain = 1) Then
		Game\Stage\Rain = CreateTemplate()
		
		
			SetTemplateEmitterBlend(Game\Stage\Rain, 1)
			SetTemplateInterval(Game\Stage\Rain, 1)
			SetTemplateParticlesPerInterval(Game\Stage\Rain, 15)
			SetTemplateEmitterLifeTime(Game\Stage\Rain, 25)
			SetTemplateParticleLifeTime(Game\Stage\Rain, 25, 25)
			SetTemplateTexture(Game\Stage\Rain, "Textures\Rain.png", 3)
			SetTemplateOffset(Game\Stage\Rain, -120, 120, 40, 40, -120, 120)
			SetTemplateVelocity(Game\Stage\Rain, 0, 0, -4.23*Game\Deltatime\Delta#, -4.03*Game\Deltatime\Delta#, 0, 0)
			SetTemplateSize(Game\Stage\Rain, .9, .9)
			SetTemplateAlphaVel(Game\Stage\Rain, True)
			SetTemplateMaxParticles(Game\Stage\Rain,21)
			SetEmitter(c\Entity,Game\Stage\Rain)
		EndIf
		
		If (Game\Stage\Properties\Snow = 1)
			Game\Stage\Snow = CreateTemplate()
			
			SetTemplateParticleLifeTime(Game\Stage\Snow, 45, 45)
		;	SetTemplateAlpha(Game\Stage\Snow, 1)
			SetTemplateEmitterBlend(Game\Stage\Snow, 1)
			SetTemplateInterval(Game\Stage\Snow, 1)
			SetTemplateParticlesPerInterval(Game\Stage\Snow, 1)
			SetTemplateEmitterLifeTime(Game\Stage\Snow, 45)
			SetTemplateTexture(Game\Stage\Snow, "Textures\Snow.png", 3)
			SetTemplateOffset(Game\Stage\Snow, -120, 120, 40, 40, -120, 120)
			SetTemplateVelocity(Game\Stage\Snow, .1, .1, -1.83*Game\Deltatime\Delta#, -1.83*Game\Deltatime\Delta#, 0, 0)
				;SetTemplateGravity(Rain, .3)
			SetTemplateSize(Game\Stage\Snow, .9, .9)
			SetTemplateAlphaVel(Game\Stage\Snow, True)
				;SetTemplateAlpha(Rain, 1)
				;SetTemplateFixAngles(Rain, 0, 1)
			SetTemplateMaxParticles(Game\Stage\Snow,10)
			SetEmitter(c\Entity,Game\Stage\Snow)
		EndIf
		
		Game\Others\Explode = CreateTemplate()
			
		
		
	Next
	
		; ---------------
	
		; Once finished loading stage information, activate music, setup collisions and
		; then, we're ready to go to next step.
	;	PlaySound(Game\Stage\Properties\Music)

		; Setup collisions within the environment.
		Collisions(COLLISION_PLAYER, COLLISION_WORLD_POLYGON, 		2, 2)
		Collisions(COLLISION_PLAYER, COLLISION_WORLD_POLYGON_ALIGN, 2, 2)
		Collisions(COLLISION_PLAYER, COLLISION_WORLD_BOX,			3, 2)
		Collisions(COLLISION_CAMERA, COLLISION_WORLD_POLYGON, 		2, 1)
		Collisions(COLLISION_CAMERA, COLLISION_WORLD_POLYGON_ALIGN,	2, 1)
		Collisions(COLLISION_CAMERA, COLLISION_WORLD_BOX,			3, 1)
		
		Collisions(COLLISION_BOXDETECT, COLLISION_WORLD_POLYGON, 		2, 2)
		Collisions(COLLISION_BOXDETECT, COLLISION_WORLD_POLYGON_ALIGN, 2, 2)
		Collisions(COLLISION_BOXDETECT, COLLISION_WORLD_BOX,			3, 2)
		Collisions(COLLISION_BOXDETECT, COLLISION_WORLD_LEDGE,			2, 2)
		
		Collisions(COLLISION_PLAYER, COLLISION_WORLD_HURT, 			2, 2)
		Collisions(COLLISION_PLAYER, COLLISION_WORLD_DIE, 			2, 2)
		
		Collisions(COLLISION_PLAYER, COLLISION_WORLD_RAIL, 			2, 2)
		
		Collisions(COLLISION_GRINDAFFECTOR, COLLISION_WORLD_RAIL, 			2, 2)
		
		; Setup Collisions between spewed rings And environment.
		Collisions(COLLISION_SPEWRING, COLLISION_WORLD_POLYGON,			2, 2)
		Collisions(COLLISION_SPEWRING, COLLISION_WORLD_POLYGON_ALIGN,	2, 2)
		Collisions(COLLISION_SPEWRING, COLLISION_WORLD_BOX,				3, 2)
		
		Collisions(COLLISION_RING, COLLISION_WORLD_POLYGON,			2, 2)
		Collisions(COLLISION_RING, COLLISION_WORLD_POLYGON_ALIGN,	2, 2)
		Collisions(COLLISION_RING, COLLISION_WORLD_BOX,				3, 2)
		
	;	Mov = OpenMovie("SafeLanding1.avi")
		
		DeltaTime_Reset(Game\DeltaTime)
		Game\State = GAME_STATE_STEP
		
		ModInput(3)
				
		; Create fade-in transition effect
		PostEffect_Create_FadeIn(0.01, 10, 10, 10)
		StopChannel(Game\MusicChn)
		Game\MusicChn = PlaySound(Game\Stage\Properties\StartMusic)
		For p.tPlayer = Each tPlayer
			
		;	p\Listener = CreateListener(p\Objects\Entity, 0, 1, 1)
	;		If (ListenerCreated = 0) Then
	;		For	c.tCamera= Each tCamera
	;			c\Listener = CreateListener(c\Entity, 0.006, 0.1, 1)
	;			ListenerCreated = 1
	;		Next
	;	EndIf
		
			p\Action = Game\Stage\StartAction
		
		Input_Lock = True
		
	Next
		;PARTICLE RAIN SETUP 1
;		Game\Stage\Rain = CreateTemplate()
;		
;		If (Game\Stage\Properties\Rain = 1) Then
;			SetTemplateEmitterBlend(Game\Stage\Rain, 1)
;			SetTemplateInterval(Game\Stage\Rain, 1)
;			SetTemplateParticlesPerInterval(Game\Stage\Rain, 15)
;			SetTemplateEmitterLifeTime(Game\Stage\Rain, 25)
;			SetTemplateParticleLifeTime(Game\Stage\Rain, 25, 25)
;			SetTemplateTexture(Game\Stage\Rain, "Textures\Rain.png", 3)
;			SetTemplateOffset(Game\Stage\Rain, -120, 120, 40, 40, -120, 120)
;			SetTemplateVelocity(Game\Stage\Rain, 0, 0, -4.23*Game\Deltatime\Delta#, -4.03*Game\Deltatime\Delta#, 0, 0)
;			SetTemplateSize(Game\Stage\Rain, .9, .9)
;			SetTemplateAlphaVel(Game\Stage\Rain, True)
;			SetTemplateMaxParticles(Game\Stage\Rain,21)
;			SetEmitter(c\Entity,Game\Stage\Rain)
;			SetTemplateAlpha(Game\Stage\Rain, 1)
;		ElseIf (Game\Stage\Properties\Rain = 0) Then
;			SetTemplateAlpha(Game\Stage\Rain, 0)
;		EndIf
	
					SetTemplateAnimTexture(Game\Others\Explode, "Textures\Explode.png", 3, 1, 64, 64, 39)
	;SetTemplateTexture(Game\Others\Explode, "Textures\Dust.png", 2, 1)
	
	For p.tPlayer = Each tPlayer
		
	;	If (Game\Stage\Properties\Rain = 1) Then SetEmitter(p\Objects\TempPiv, Game\Stage\Rain)
		
	;	If (Game\Stage\Properties\Snow = 1) Then SetEmitter(p\Objects\TempPiv, Game\Stage\Snow)
		
	
		
		p\BounceHeight = 0.7
		
			If (Game\Stage\StartPos = 1) Then
				EntityType(p\Objects\Entity, 0)
				
				PositionEntity p\Objects\Entity, Game\Stage\StartX#, Game\Stage\StartY#, Game\Stage\StartZ#
				PositionEntity p\Objects\Mesh, Game\Stage\StartX#, Game\Stage\StartY#, Game\Stage\StartZ#
				p\Motion\Speed\x = 0
				p\Motion\Speed\y = 0
				p\Motion\Speed\z = 0
				
				EntityType(p\Objects\Entity, COLLISION_PLAYER)
			;	Game\Stage\StartPos=0
			EndIf
			
				If (Game\Stage\StartPos = 0) Then
				EntityType(p\Objects\Entity, 0)
				
				Game\Stage\StartX# = Game\Stage\DefStartX#
				Game\Stage\StartY# = Game\Stage\DefStartY#
				Game\Stage\StartZ# = Game\Stage\DefStartZ#
				
				PositionEntity p\Objects\Entity, Game\Stage\DefStartX#, Game\Stage\DefStartY#, Game\Stage\DefStartZ#
				PositionEntity p\Objects\Mesh, Game\Stage\DefStartX#, Game\Stage\DefStartY#, Game\Stage\DefStartZ#				
				p\Motion\Speed\x = 0
				p\Motion\Speed\y = 0
				p\Motion\Speed\z = 0
				EntityType(p\Objects\Entity, COLLISION_PLAYER)
			EndIf
			
							;CHECKPOINT POSITION DEFAULT
			
				p\CheckpointX# = Game\Stage\StartX#
				p\CheckpointY# = Game\Stage\StartY#
				p\CheckpointZ# = Game\Stage\StartZ#
			
				Game\Stage\StartPos = 0
				p\FallTimer = MilliSecs() + 300
			
			Next
			
			
			
		;	For m.MeshStructure = Each MeshStructure : For c.tCamera = Each tCamera : RenderEntity (m\Entity, c\Entity) : Next : Next
			
			SetFont GameFont
		
		;PostEffect_Create_MotionBlur(0.84)
End Function

	; ---------------------------------------------------------------------------------------------------------
	; Game_Stage_Step
	; ---------------------------------------------------------------------------------------------------------
Function Game_Stage_Step()
	
	If online=1
		For p.tPlayer = Each tPlayer
			CalculPlayerInfo(p)
		Next	
		XX = XX+(game\deltaTime\timeCurrentFrame-Game\DeltaTime\TimePreviousFrame)
		If XX > 25		
			BP_UpdateNetwork()
			BP_UDPMessage(0,1,PlayerPositionX)
			BP_UDPMessage(0,2,PlayerPositionY)
			BP_UDPMessage(0,3,PlayerPositionZ)	
			BP_UDPMessage(0,4,PlayerRotationX)
			BP_UDPMessage(0,5,PlayerRotationY)
			BP_UDPMessage(0,6,PlayerRotationZ)
			BP_UDPMessage(0,7,PlayerAnimation)
			HandleMessages()
		EndIf 
	EndIf
	
	
		
		; SEND PLAYER BACK TO START
		
	If (Paused = False) Then
		
			If (KeyHit(KEY_F1)) And (Goal = 0) Then
				For p.tPlayer = Each tPlayer
					EntityType(p\Objects\Entity, 0)
					
					PositionEntity p\Objects\Entity, Game\Stage\StartX#, Game\Stage\StartY#, Game\Stage\StartZ#
					PositionEntity p\Objects\Mesh, Game\Stage\StartX#, Game\Stage\StartY#, Game\Stage\StartZ#
					
					Stage_Action_Tilt(0, -1, 0, 0, 0)
					
					p\Motion\Speed\x = 0
					p\Motion\Speed\y = 0
					p\Motion\Speed\z = 0
				;	p\Action = ACTION_FALL
					
					EntityType(p\Objects\Entity, COLLISION_PLAYER)
					p\Action = Game\Stage\StartAction
				Next
				For c.tCamera = Each tCamera
					c\Held = 0
				Next
			EndIf
			
			For p.tPlayer = Each tPlayer
				If (KeyHit(KEY_F2)) And (Not p\Action = ACTION_HURT) And (Goal = 0) Then
				
					EntityType(p\Objects\Entity, 0)
					
					PositionEntity p\Objects\Entity, p\CheckpointX#, p\CheckpointY#, p\CheckpointZ#
					PositionEntity p\Objects\Mesh, p\CheckpointX#, p\CheckpointY#, p\CheckpointZ#	
					
					Stage_Action_Tilt(0, -1, 0, 0, 0)
					
					p\Motion\Speed\x = 0
					p\Motion\Speed\y = 0
					p\Motion\Speed\z = 0
					p\Action = ACTION_HURT
					p\LoseRings = 0
					p\LoseLife = 0
					
					EntityType(p\Objects\Entity, COLLISION_PLAYER)
				;	p\Action = Game\Stage\StartAction
					
				For c.tCamera = Each tCamera
					c\Held = 0
				Next
			EndIf
		Next
			
			If (Input\Pressed\CopyPos) Then
				For p.tPlayer = Each tPlayer
					api_EmptyClipboard
				;api_SetClipboardData cb_TEXT, "x=" + EntityX#(p\Objects\Entity) + " y=" + EntityY#(p\Objects\Entity) + " z="  + EntityZ#(p\Objects\Entity)
					WriteClipboardText("<position x=" + Chr$(34) + EntityX#(p\Objects\Entity) + Chr$(34) + " y=" + Chr$(34) + EntityY#(p\Objects\Entity) + Chr$(34) + " z="  + Chr$(34) + EntityZ#(p\Objects\Entity) + Chr$(34)+"/>")
					PlaySound(Sound_Target)
				Next
			EndIf
			
			If (Input\Pressed\CopyRot) Then
				For p.tPlayer = Each tPlayer
					api_EmptyClipboard
				;api_SetClipboardData cb_TEXT, "x=" + EntityX#(p\Objects\Entity) + " y=" + EntityY#(p\Objects\Entity) + " z="  + EntityZ#(p\Objects\Entity)
					WriteClipboardText("<rotation pitch=" + Chr$(34) + EntityPitch#(p\Objects\Mesh) + Chr$(34) + " yaw=" + Chr$(34) + p\Animation\Direction# + Chr$(34) + " roll="  + Chr$(34) + EntityRoll#(p\Objects\Mesh)+ Chr$(34)+"/>")
					PlaySound(Sound_Target)
				Next
			EndIf
			
			If (Input\Pressed\CopyDest) Then
				For p.tPlayer = Each tPlayer
					api_EmptyClipboard
				;api_SetClipboardData cb_TEXT, "x=" + EntityX#(p\Objects\Entity) + " y=" + EntityY#(p\Objects\Entity) + " z="  + EntityZ#(p\Objects\Entity)
					WriteClipboardText("x=" + Chr$(34) + EntityX#(p\Objects\Entity) + Chr$(34) + " y=" + Chr$(34) + EntityY#(p\Objects\Entity) + Chr$(34) + " z="  + Chr$(34) + EntityZ#(p\Objects\Entity) + Chr$(34)+"/>")
					PlaySound(Sound_Target)
				Next
			EndIf
			
			If (Debug = 1) Then
				
			;	For p.tPlayer = Each tPlayer
			;		p\Action = ACTION_FALL
			;		p\Motion\Speed\x# = 0
			;	p\Motion\Speed\y# = 0
			;	p\Motion\Speed\z# = 0
			;	
			;	If (KeyDown(KEY_F)) Then MoveEntity(p\Objects\Entity,0,3*Game\Deltatime\Delta#,0)
			;	If (KeyDown(KEY_G)) Then MoveEntity(p\Objects\Entity,0,-3*Game\Deltatime\Delta#,0)
			;	
			;	If (KeyDown(KEY_W)) Then MoveEntity(p\Objects\Entity,-1*Game\Deltatime\Delta#,0,0)
			;	If (KeyDown(KEY_S)) Then MoveEntity(p\Objects\Entity,1*Game\Deltatime\Delta#,0,0)
			;	If (KeyDown(KEY_A)) Then p\Animation\Direction# = p\Animation\Direction# + 2*Game\Deltatime\Delta# ;TurnEntity(p\Objects\Entity,0,0,-1*Game\Deltatime\Delta#)
			;	If (KeyDown(KEY_D)) Then p\Animation\Direction# = p\Animation\Direction# - 2*Game\Deltatime\Delta# ;TurnEntity(p\Objects\Entity,0,0,1*Game\Deltatime\Delta#)
			;	
			;	If (KeyDown(KEY_LEFT)) Then TurnEntity(p\Objects\Entity,-1*Game\Deltatime\Delta#,0,0) : If (KeyDown(KEY_LEFT)) Then TurnEntity(p\Objects\Mesh,-1*Game\Deltatime\Delta#,0,0)
			;	If (KeyDown(KEY_UP)) Then TurnEntity(p\Objects\Entity,0,-1*Game\Deltatime\Delta#,0)
				;If (KeyDown(KEY_RIGHT)) Then TurnEntity(p\Objects\Entity,0,0,-1*Game\Deltatime\Delta#)
				For p.tPlayer = Each tPlayer
					
					If (Input\Hold\Hover) Then
				p\Motion\Speed\y# = 1.7
				p\Action = ACTION_FALL
				p\Motion\Ground = False
			EndIf
		Next
		EndIf
			
				
	
			; Update game timer and fps count
			Game\Gameplay\Time = Game\Gameplay\Time+(Game\DeltaTime\TimeCurrentFrame-Game\DeltaTime\TimePreviousFrame)
			Game\Others\Frames = Game\Others\Frames + 1
			If (Game\Others\NextFrame < Game\DeltaTime\TimeCurrentFrame) Then
				Game\Others\FPS   	  = Game\Others\Frames
				Game\Others\Frames	  = 0
				Game\Others\NextFrame = Game\DeltaTime\TimeCurrentFrame+1000
			End If
	
			; Update gravity alignment
		AlignToVector(Game\Stage\Gravity, Game\Stage\GravityAlignment\x#, Game\Stage\GravityAlignment\y#, Game\Stage\GravityAlignment\z#, 2)
					
			; Update objects
		For c.tCamera = Each tCamera
			If (Game\Stage\Properties\Sun <> 0) Then
				Camera_Update(c, Game\DeltaTime, Game\Stage\Properties\Sun)
				Else Camera_Update(c, Game\DeltaTime, 0)
		EndIf
		;	Camera_Update(c, Game\DeltaTime, Game\Stage\Properties\Sun)
		;	If (FXEnabled = 1) Then UpdateShadows (c\Entity)
			UpdateAAMeshes(c\Entity)
		Next
		For c.tCamera = Each tCamera : RenderEntity Game\Stage\Properties\SkyBox, c\Entity, 0, 1 : Next
			For p.tPlayer = Each tPlayer : Player_Update(p, Game\DeltaTime) : Next
		For p2.tPlayer2 = Each tPlayer2 : If (p2\Objects\Entity2<>0) Then : Player_Update2(p2, Game\DeltaTime) : EndIf : Next
			
		For p.tPlayer = Each tPlayer : p\Frame = RecursiveAnimTime (p\Objects\Mesh) : Next
		
		
			
			If (Goal = 1) Then
				GoalEnd(p.tPlayer, d.tDeltaTime)
			;	ModInput(3)
			;	Goal = 0
			EndIf
			
			GlobalTimer = (game\deltaTime\timeCurrentFrame-Game\DeltaTime\TimePreviousFrame)
			GlobalTimerFloat# = (game\deltaTime\timeCurrentFrame-Game\DeltaTime\TimePreviousFrame)
			
			For m.MeshStructure = Each MeshStructure
				
				
			;	CreateAAMesh(p\Objects\Entity,c\Entity,5,3,0.5,1,255,255,255,1,1.0,1,0)
			
			Next
			
			Objects_Update(Game\DeltaTime)
			Segments_Update(Game\DeltaTime)
			Vert_UpdatePointmasses()
			Vert_UpdateConstraints()
			
			; Loop Transition
			If ChannelPlaying(Game\MusicChn)=False Then
			StopChannel(Game\MusicChn)
			Game\MusicChn = PlaySound(Game\Stage\Properties\Music)
		EndIf
			;------------------
			
;			For p.tPlayer = Each tPlayer 
;			If (p\Homing = 1) Then
;			MoveEntity(p\Objects\Entity,0,3*Game\DeltaTime\Delta#,0)
;		EndIf
;	Next
		
						;If (Game\Gameplay\NPCTextTimer > MilliSecs()) Then
		If (Game\Gameplay\NPCTalking = 1) Then
			ModInput(3)
			
			If (Input\Pressed\Interact And Game\Gameplay\NPCTalking = 1) Then
				Game\Gameplay\NPCTalking = 0
			EndIf
			
			Rect 350*GAME_WINDOW_SCALE#, 400*GAME_WINDOW_SCALE#, 350*GAME_WINDOW_SCALE#, 400*GAME_WINDOW_SCALE#, 1
	;	ElseIf (Game\Gameplay\NPCTextTimer < MilliSecs()) Then
		ElseIf (Game\Gameplay\NPCTalking = 0) Then
			Game\Gameplay\NPCText$ = ""
			
			For p.tPlayer = Each tPlayer
			
			If (p\Homing = 0) Then ModInput(2)
			If (p\Homing = 1) Then ModInput(4)
			
			Next
			
		EndIf
		
		For p.tPlayer = Each tPlayer
			If (p\Action = ACTION_HURT Or p\Action = ACTION_WALLHIT) Then
			ModInput(3)
	;	Else
	;		ModInput(2)
		EndIf
	Next
	
	For p.tPlayer = Each tPlayer
	
		If ((p\LoseLife = 1) And (Not p\Action = ACTION_HURT)) Then
			
				EntityType(p\Objects\Entity, 0)
				
				PositionEntity p\Objects\Entity, p\CheckpointX#, p\CheckpointY#, p\CheckpointZ#
				PositionEntity p\Objects\Mesh, p\CheckpointX#, p\CheckpointY#, p\CheckpointZ#						
				p\Motion\Speed\x = 0
				p\Motion\Speed\y = 0
				p\Motion\Speed\z = 0
			;	p\Action = ACTION_FALL
				
				EntityType(p\Objects\Entity, COLLISION_PLAYER)
				
				p\Action = Game\Stage\StartAction
				
				Game\Gameplay\Rings = 0
				Game\Gameplay\Lives = Game\Gameplay\Lives - 1
				
				p\LoseLife = 0
			For c.tCamera = Each tCamera
				c\Held = 0
			Next
			
		EndIf
		
	Next
	
	For p.tPlayer = Each tPlayer
;	If (FXEnabled = 1) Then
;		EntityAlpha(p\Objects\Shadow, 0)
;	Else
;		EntityAlpha(p\Objects\Shadow, 1)
;	EndIf
		
		If (p\UsingTerminal = True) Then
			Input_Lock = False
			
		Else
			Input_Lock = True
		EndIf
		
Next
		
	;			
	;			AlignToVector(p\Objects\JumpImage3, p\Im2X#, p\Im2Y#, p\Im2Z#, 2, .80)
	;			AlignToVector(p\Objects\JumpImage2, p\Im1X#, p\Im1Y#, p\Im1Z#, 2, .80)
	;			AlignToVector(p\Objects\JumpImage1, EntityX#(p\Objects\Entity), EntityY#(p\Objects\Entity), EntityZ#(p\Objects\Entity), 2, .80)
	;		
	;			MoveEntity(p\Objects\JumpImage1,0,6*Game\DeltaTime\Delta#,0)
	;			MoveEntity(p\Objects\JumpImage2,0,4*Game\DeltaTime\Delta#,0)
	;			MoveEntity(p\Objects\JumpImage3,0,2.5*Game\DeltaTime\Delta#,0)
			
			If (ChannelPlaying(Channel_Rain) = False) And (Game\Stage\Properties\Rain = 1) Then
				Channel_Rain = PlaySound(Sound_Rain)
			EndIf
			
			If (ChannelPlaying(Channel_Hint)=True) Then ChannelVolume (Game\MusicChn, 0.4) : Else ChannelVolume (Game\MusicChn, 1)
			
		;	For e.Particle = Each Particle
				
			
			UpdateTrails()
		;	RP_Update()
	;		PW_UpdateEmitters()
	;		PW_UpdateParticles()
			
	;		For c.tCamera = Each tCamera
	;			UpdateRain3D(c\Entity,40)
	;	Next
			
			; Update world
			UpdateWorld(Game\DeltaTime\Delta)
			
			; Buttons
			;	Cls
			mx = MouseX () : my = MouseY() ; grab the mouse position at the top of the loop
			
			;updatebuttons() ; check rollover state and draw buttons to screen. Call this every loop.
			;ButtonAction() ;this will execute the results of any button presses.
			;	Flip
			;---------
			
			UpdateParticles()
			
			For p.tPlayer = Each tPlayer
				
			
			
			
			If Game\Online = 1 Then
				
			If Game\Player1 = 1 Then
			SendNetMsg Rand(1,99), p\Action, "Player2", 0
			SendNetMsg Rand(1,99), p\Animation\Direction#, "Player2", 0
			EndIf
			
			
			EndIf
		Next
			; Update water texture
			If (FxManager_Supported = True) Then
				PositionTexture(Game\Stage\Properties\WaterDistortion, Cos(MilliSecs()*0.002)*4, Sin(MilliSecs()*0.002)*4)
	
				If (Game\Stage\Properties\Water <> 0) Then
					EntityTexture(Game\Stage\Properties\WaterFX\NormalMesh, Game\Stage\Properties\WaterDistortion, (MilliSecs()*0.03) Mod 31, 0)
				End If
			End If
			
			For c.tCamera = Each tCamera
		;		MirrorCamera c\Entity, WaterPlane
			;Now for Update the cubemap
			;	PositionEntity(Game\Stage\Properties\SkyBox, EntityX(CubeCamera), EntityY(CubeCamera), EntityZ(CubeCamera)) ;Position the Skybox in the cube map camera, If not, the BG will be black
			;	Wat_UpdateCubeMap(CubeMapTex, 0, c\Entity, CMWater, Game\Stage\Properties\WaterLevel,EntityY#(c\Entity)-50) ;1°- Texture, 2°- The cube camera we created, 3°- The normal camera, 4°- The custom mesh Water, 5°- The Water Level, 6°- An value to add to the CubeCam position, ajust it as your pleasure!
			;	Wat_UpdateCubeMap(CubeMapTex, 0, c\Entity, Game\Stage\Properties\Water, Game\Stage\Properties\WaterLevel,EntityY#(c\Entity)-50)
				Wat_UpdateWaterReflection(CubeMapTex,c\Entity, CMWater)
			;	PositionEntity(Game\Stage\Properties\SkyBox, EntityX(c\Entity), EntityY(c\Entity), EntityZ(c\Entity)) ;Return the Skybox for the camera
				
			Next
			
			
		;	If (Game\Stage\Properties\WaterTexture <> 0) Then PositionTexture(Game\Stage\Properties\WaterTexture,    0, MilliSecs()*0.007)
		End If
		
		; Render world
		If (KeyHit(KEY_CTRL_LEFT)) Then FxManager_Activated = 1-FxManager_Activated
		For c.tCamera = Each tCamera
			CameraProjMode (c\Entity, 1)
			PositionEntity(Game\Stage\Properties\SkyBox, EntityX(c\Entity), EntityY(c\Entity), EntityZ(c\Entity))
			PositionEntity(Game\Stage\Properties\Water, Cos(MilliSecs()*0.02)*4, EntityY#(Game\Stage\Properties\Water, True), Sin(MilliSecs()*0.02)*4)

			If (Game\Stage\Properties\Water <> 0) Then
				If EntityY(c\Entity) < Game\Stage\Properties\WaterLevel Then
					CameraFogMode c\Entity, 1
					CameraFogColor c\Entity, 70, 80, 120
					CameraFogRange c\Entity, 0.1, 90
					RotateEntity Game\Stage\Properties\Water, 0, 0, 180
					EntityColor Game\Stage\Properties\Water, 255, 255, 255
					EntityBlend Game\Stage\Properties\Water, 1
				Else
					CameraFogMode c\Entity, 0
					RotateEntity Game\Stage\Properties\Water, 0, 0, 0
				;	EntityColor Game\Stage\Properties\Water, 12, 73, 135
					EntityColor Game\Stage\Properties\Water, 255, 255, 255
				EndIf
			End If
			
			For p.tPlayer = Each tPlayer
		;		If (EntityY(c\Entity) < Game\Stage\Properties\WaterLevel) And (ChannelPlaying(Channel_Underwater)=False) Then
		;			Channel_Underwater = PlaySound(Sound_Underwater)
		;		Else
		;			StopChannel(Channel_Underwater)
		;		EndIf
			Next
			
			AnimateWater()
			
	;		If (DebugEnabled = 1) Then
	;		If (KeyHit(KEY_CTRL_RIGHT)) Then Debug = 1-Debug
	;	EndIf
			
			
			
			;FxManager_RenderWorldFast()
			
		;	Locate (300*GAME_WINDOW_SCALE#, 600*GAME_WINDOW_SCALE#)
			;EntityType (c\Entity, 3)
			CameraProjMode (c\Entity, 0)
			
			PostEffect_UpdateAll(Game\DeltaTime)
			
			
					; INITIATE FASTEXTENSION
			For p.tPlayer = Each tPlayer
	;			For c.tCamera = Each tCamera
			
			
				CustomPostprocessGlow(0.67, 4, 2, 0.35, 1, 255, 255, 255, 0)
			CustomPostprocessDOF(450,1220,1,3,.07)
			If BetaTapeMode = 1 Then CustomPostprocessContrast(.84,0,225,225,225,2) : Else CustomPostprocessContrast(.44,0,225,225,225,2)
		;	CustomPostProcessBlurMotion 0.5, 0, 0, 0.7, 0.7
			CustomPostprocessRays 0.4, 0.5, 105, 0.25
			
			
			
			If MBlurAmount# < 0.9 Then MBlurAmount# = ((p\Motion\GroundSpeed#-2.2)/6.5)
			If MBlurAmount# >= 0.9 Then MBlurAmount# = 0.9
			
			CustomPostprocessBlurZoom 0.5, 0.5, 125, MBlurAmount#, 4, 1, 255, 255, 255, fadetexture
			CustomPostprocessBlurMotion 0.53,  0, 0, 0.5,  0.5,  100.0,  100.0,  0,     0,  255,  255,  255
			CustomPostprocessBlurDirectional (c\Rotation\y#-(p\Animation\Direction#-90)), 0.3, 4, 0.51, 1
			
		;	CustomPostprocessBlurSpin (0.5, 0.5, 4, Gameplay_Camera_RotationSpeedY#, 4, 1, 255, 255, 255, 0)
			
			
		;	CustomPostprocessBlurZoom 0.5, 0.5, 125, p\Motion\GroundSpeed#/10, 4, 1,  255,  255,  255, FadeTexture
			
			
			
			If (FXEnabled = 1) Then
				RenderPostprocess FE_DOF
				RenderPostprocess FE_Contrast
				RenderPostprocess FE_Glow
				If (p\BB_InMove=1) Then RenderPostprocess FE_Overlay
				RenderPostprocess FE_BlurMotion
			;	If (ColourOn=1) Then RenderPostprocess FE_Rays
				RenderPostprocess FE_BlurZoom
			;	If BetaTapeMode = 1 Then RenderPostprocess FE_Posterize
			;	RenderPostprocess FE_BlurSpin
		;	RenderPostprocess FE_Inverse
		;	Bump
				
		;	TextureAnisotropy(5,-1)
				
		;	RenderEntity Game\Stage\Properties\SkyBox, c\Entity, 0, 1
		;	RenderEntity p\Objects\Mesh, c\Entity, 0, 0
				
				If EntityY(c\Entity) < Game\Stage\Properties\WaterLevel Then RenderPostprocess FE_Blur : CustomPostprocessBlur (0.7, 4, 0.08)
				
				If BetaTapeMode = 1 And EntityY(c\Entity) > Game\Stage\Properties\WaterLevel Then RenderPostprocess FE_Blur : CustomPostprocessBlur (0.8, 4, 0.09); : RenderPostprocess FE_BlurSpin
				
				
				If p\BB_InMove = True Then RenderPostProcess FE_BlurDirectional
			EndIf
	;	Next
	Next
			
			
		; RENDER SKYBOX
		;	For c.tCamera = Each tCamera : RenderEntity Game\Stage\Properties\SkyBox, c\Entity, 0, 0 : Next
				
			
	;RenderEntity Game\Stage\Properties\SkyBox, c\Entity, 0, 0
			
			
		;	For p.tPlayer = Each tPlayer : GroupAttach RG_Glow,p\Objects\Mesh : Next
	
	
	
;	If ShowHUD = 1 Then
	;	If Goal=0 Then
			Interface_Render()
	;	EndIf
;	EndIf
		
		For p.tPlayer = Each tPlayer
		If p\UsingTerminal = 1 Then GUI_UpdateGUI()
		Next
		Next 
		
		
		
		
		
	;	For p.tPlayer = Each tPlayer : RenderGroup RG_BlurMotion,p\Objects\Mesh,4 : Next
		
	;	WrapText(250*GAME_WINDOW_SCALE#, 425*GAME_WINDOW_SCALE#, "BETA", 110*GAME_WINDOW_SCALE#,0,0,0, 255,0,0, 1)
		
		;WrapText(230*GAME_WINDOW_SCALE#, 95*GAME_WINDOW_SCALE#, SoundLoaded$, 330*GAME_WINDOW_SCALE#,0,0,0, 200,200,255, 1)
		
		For d.tDeltaTime = Each tDeltaTime
		For p.tPlayer = Each tPlayer
			
			For t.trail = Each trail
				SpeedLength# = Sqr#(p\Motion\Speed\x#^2+p\Motion\Speed\z#^2)
											; Declarate acceleration and speed vectors and setup.
				Acceleration.tVector 		= Vector(Cos#(MotionDirection#)*MotionPressure#, 0, Sin#(MotionDirection#)*MotionPressure#)
				Speed.tVector 		 		= Vector(p\Motion\Speed\x#, 0, p\Motion\Speed\z#)
				SpeedCompensation.tVector	= Vector(0, 0, 0)
				Speed_Length#				= Vector_Length#(Speed)
				
				For c.tCamera = Each tCamera
			;	WrapText(230*GAME_WINDOW_SCALE#, 85*GAME_WINDOW_SCALE#, c\Held, 330*GAME_WINDOW_SCALE#,0,0,0, 200,200,255, 1)
			Next
			Next
			
			
			If (p\UsingTerminal = 1) Then
				ModInput(1) : Input_Lock = False
			EndIf
			
			If (Goal = 1) Then
				ModInput(1)
				
				
				For c.tCamera = Each tCamera
					c\DistanceFromTarget# = 12
					c\TargetRotation\y# = RotateTowardsAngle#(c\TargetRotation\y#, c\Target\Animation\Direction#+10, 40)
					c\TargetRotation\x# = RotateTowardsAngle#(c\TargetRotation\x#, -20, 40)
					c\Rotation\y# = RotateTowardsAngle#(c\TargetRotation\y#, c\Target\Animation\Direction#+50, 40)
					p\Motion\Speed\X# = 0
					p\Motion\Speed\Z# = 0
				Next
				
				
		;		WrapText(100*GAME_WINDOW_SCALE#, 140*GAME_WINDOW_SCALE#, "Rings: " + Game\Gameplay\Rings, 330*GAME_WINDOW_SCALE#,0,0,0, 250,200,255, 1)
		;		WrapText(100*GAME_WINDOW_SCALE#, 165*GAME_WINDOW_SCALE#, "Time: " + Left(Game\Gameplay\TimeResult, 4), 330*GAME_WINDOW_SCALE#,0,0,0, 170,200,255, 1)
		;		WrapText(100*GAME_WINDOW_SCALE#, 190*GAME_WINDOW_SCALE#, "Score: " + Game\Gameplay\Score, 330*GAME_WINDOW_SCALE#,0,0,0, 90,200,255, 1)
			EndIf
			
		;	WrapText(200*GAME_WINDOW_SCALE#, 30*GAME_WINDOW_SCALE#, Sounds[i]\Sound, 330*GAME_WINDOW_SCALE#,0,0,0, 200,200,255, 1)
			
			
			If (p\DiveTimer > 1700) Then WrapText(20*GAME_WINDOW_SCALE#, 140*GAME_WINDOW_SCALE#, "ActionA: Skydive", 130*GAME_WINDOW_SCALE#,0,0,0, 0,255,0, 1)
			
			If (p\BB_CanDo = 1) Then WrapText(20*GAME_WINDOW_SCALE#, 165*GAME_WINDOW_SCALE#, "ActionB: Barrier Blast", 130*GAME_WINDOW_SCALE#,0,0,0, 255,0,0, 1)
			
			If (Debug = 1) Then
				WrapText(230*GAME_WINDOW_SCALE#, 5*GAME_WINDOW_SCALE#, "Start Position: " + Game\Stage\StartX + Game\Stage\StartY + Game\Stage\StartZ, 330*GAME_WINDOW_SCALE#,0,0,0, 200,200,255, 1)
				WrapText(230*GAME_WINDOW_SCALE#, 30*GAME_WINDOW_SCALE#, "Position: " + Int(EntityX(p\Objects\Entity)) +" "+ Int(EntityY(p\Objects\Entity)) +" "+ Int(EntityZ(p\Objects\Entity)), 330*GAME_WINDOW_SCALE#,0,0,0, 200,200,255, 1)
				WrapText(230*GAME_WINDOW_SCALE#, 55*GAME_WINDOW_SCALE#, "Rotation: " + Int(EntityPitch(p\Objects\Mesh)) +" "+ Int(p\Animation\Direction) +" "+ Int(EntityRoll(p\Objects\Mesh)), 330*GAME_WINDOW_SCALE#,0,0,0, 200,200,255, 1)
			;	WrapText(5*GAME_WINDOW_SCALE#, 5*GAME_WINDOW_SCALE#, "'Q' Hover", 200*GAME_WINDOW_SCALE#,255,255,255, 255,0,0, 1)
			;	WrapText(5*GAME_WINDOW_SCALE#, 25*GAME_WINDOW_SCALE#, "',' Copy Position (Float Value)", 200*GAME_WINDOW_SCALE#,255,255,255, 255,0,0, 1)
			;	WrapText(5*GAME_WINDOW_SCALE#, 45*GAME_WINDOW_SCALE#, "'.' Copy Rotation (Float Value)", 200*GAME_WINDOW_SCALE#,255,255,255, 255,0,0, 1)
				
				WrapText(230*GAME_WINDOW_SCALE#, 80*GAME_WINDOW_SCALE#, "Start Action: " + Game\Stage\StartAction + " Bounce Height: " + p\BounceHeight#, 330*GAME_WINDOW_SCALE#,0,0,0, 200,200,255, 1)
				
				WrapText(230*GAME_WINDOW_SCALE#, 105*GAME_WINDOW_SCALE#, "BarrierBlast: " + p\Flags\BBlastEnabled + " Head Tracking: " + p\Flags\HeadTrackEnabled, 330*GAME_WINDOW_SCALE#,0,0,0, 200,200,255, 1)
			;	WrapText(230*GAME_WINDOW_SCALE#, 85*GAME_WINDOW_SCALE#, p\Motion\TurnSpeed#, 330*GAME_WINDOW_SCALE#,0,0,0, 200,200,255, 1)
				
				For c.tCamera = Each tCamera
		;			WrapText(230*GAME_WINDOW_SCALE#, 120*GAME_WINDOW_SCALE#, c\UseCoord, 330*GAME_WINDOW_SCALE#,0,0,0, 200,200,255, 1)
				Next
				
			;	Level = BASS_ChannelGetLevel(Stream)
				
				
				
				ShowEntity p\Objects\Box
			EndIf
			If (Debug = 0) Then
				HideEntity p\Objects\Box
			EndIf
		
			PositionEntity p\Objects\Box, EntityX(p\Objects\Mesh), EntityY(p\Objects\Mesh)-0.3, EntityZ(p\Objects\Mesh)
		RotateEntity(p\Objects\Box, 0, p\Animation\Direction#-90, 0)
		AlignToVector(p\Objects\Box, p\Motion\Align\x#, p\Motion\Align\y#, p\Motion\Align\z#, 2)
		
	Next
Next
		
		If (Game\Gameplay\NPCTalking = 1) Then
		;	Rect 180*GAME_WINDOW_SCALE#, 350*GAME_WINDOW_SCALE#, 330*GAME_WINDOW_SCALE#, 100*GAME_WINDOW_SCALE#, 1 : Color 0,0,0
			
			npctextheight# = 320*GAME_WINDOW_SCALE#
			
			WrapText(180*GAME_WINDOW_SCALE#, npctextheight#, Game\Gameplay\NPCText$, 330*GAME_WINDOW_SCALE#,0,0,0, 200,200,255, 1)
			WrapText(230*GAME_WINDOW_SCALE#, npctextheight# - 40, "Press 'Interact' to dismiss", 300,255,255,255, 255,0,0, 1)
		;	Text 385*GAME_WINDOW_SCALE#, 430*GAME_WINDOW_SCALE#, "Interact: Dismiss", True, True : Color 255,255,255
		EndIf
		
		If (Paused = 1) Then
		;	WrapText(300, 320, "Press 'ESC' to Resume", 330*GAME_WINDOW_SCALE#,0,0,0, 200,200,255, 1)
		;	WrapText(300, 370, "Press 'SPACE' to Exit to the Main Menu", 330*GAME_WINDOW_SCALE#,0,0,0, 200,200,255, 1) ;*GAME_WINDOW_SCALE#
		EndIf
		
		
		
	;	Text 350*GAME_WINDOW_SCALE#, 400*GAME_WINDOW_SCALE#, Game\Gameplay\NPCText$, True, True : Color 255,255,255
		
		Flip(GAME_WINDOW_VSYNC)
	End Function

	; ---------------------------------------------------------------------------------------------------------
	; Game_Stage_End
	; ---------------------------------------------------------------------------------------------------------
Function Game_Stage_End()
	
;	Game\StartGame = 0
	
	Delete_Player_and_Stage()
	
	

End Function

	; ---------------------------------------------------------------------------------------------------------
	; Game_Stage_End1
	; ---------------------------------------------------------------------------------------------------------
Function Game_Stage_End1()
	
;	Game\StartGame = 1
	
	Delete_Player_and_Stage()
	
	
;	If (GUI_Message(raddefchar, "GetChecked")=False) Then Load_Characters_List(ModelSelect$)
	
End Function
	
	; ---------------------------------------------------------------------------------------------------------
	; Delete_Player_and_Stage
	; ---------------------------------------------------------------------------------------------------------
	Function Delete_Player_and_Stage()
	;	Game\Stage\Properties\Rain = 0
	;	Game\Stage\Properties\Snow = 0
	;	StopChannel(Game\MusicChn)
	;	StopChannel(Channel_Rain)
	;	Game\Gameplay\Rings = 0
	;	Game\Gameplay\Time = 0
		
		Ready = 0
		
		PlayerCreated = 0
		Goal = 0
	;	CharType = 0
		
		Game\Gameplay\Rings = 0
		Game\Gameplay\Time = 0
		
		Game\Stage\Properties\StartMusic = LoadSound ("Sounds\Null.wav")
		
		StopChannel(Channel_RingIdle)
		PlaySound Sound_GRing
		StopChannel Game\MusicChn
		
		; Delete the player
		For p.tPlayer = Each tPlayer
			
		;Clear particles
		;	If (Game\CreatedCharacter = 1) Then
			FreeEmitter(p\Objects\TempPiv)
			FreeTemplate(p\Objects\Template)
			FreeParticles()
		;	FreeShadowCaster p\Objects\Mesh
		;	CheckPointX# = 0
		;	CheckPointY# = 0
		;	CheckPointZ# = 0
	;		If (Game\Stage\StartPosDel = 1) Then
	;			Game\Stage\StartPos = 0
	;			Game\Stage\StartX# = 0
	;			Game\Stage\StartY# = 0
	;			Game\Stage\StartZ# = 0
	;		Else
	;			Game\Stage\StartPos = 1
	;		EndIf
			Player_Destroy(p)
			
			Delete p
			
	;EndIf
		Next
		
		For p2.tPlayer2 = Each tPlayer2
			Player_Destroy2(p2)
			Delete p2
Next

		For m.MeshStructure = Each MeshStructure
			FreeEntity m\Entity
		Delete m
	Next
	
	For d.tDeltaTime = Each tDeltaTime
	d\IdealInterval		= 1/(1000/Float(d\IdealFPS))
Next
		
	;	Game\Stage\Properties\Rain = 0
	;	Game\Stage\Properties\Snow = 0
	;	FreeParticles()

			For o.tObject = Each tObject
				FreeShadowCaster o\Entity
			;	If (o\ObjType=1) Then FreeEntity(o\IValues[0]) : FreeEntity(o\IValues[1]) : FreeEntity(o\IValues[2]) : FreeEntity(o\IValues[3])
				
				If o\IValues[0] <> 0 Then FreeEntity(o\IValues[0])
				If o\IValues[1] <> 0 Then FreeEntity(o\IValues[1])
				If o\IValues[2] <> 0 Then FreeEntity(o\IValues[2])
				If o\IValues[3] <> 0 Then FreeEntity(o\IValues[3])
			FreeEntity o\Entity
			
			StopChannel(o\Channel)
		;	FreeEntity(o\Sound)
			Delete o\Position
			Delete o
		Next
		
		FreeEntity Game\Stage\Properties\SkyBox
		
	;	FreeEntity Game\Stage\Properties\Sun
	;	Game\Stage\Properties\Sun = 0
		;	FreeEntity Game\Stage\Properties\Light
		;	FreeEntity Game\Lights\Spot
		;	FreeEntity Game\Lights\Point
		;	FreeEntity Game\Lights\Dir
			
	;Delete Lights
			AmbientLight 0,0,0
			For i = 0 To 7
				For Game\Lights[i] = Each tGame_Lights
					
					FreeEntity Game\Lights[i]\Light
					Delete Game\Lights[i]
					;Delete i
				Next
			Next
			
					; Delete the camera
			For camera.tCamera = Each tCamera
				;FreeEntity camera\Listener
				Camera_Destroy(camera)
			Next
			
			DeInitPostProcess
			
			FreeEntity Game\Stage\Properties\Water
		;	FreeTexture Game\Stage\Properties\WaterTexture
			FreeTexture Game\Stage\Properties\WaterDistortion
			
			Game\CreatedCharacter = 0
			
			BetaTapeMode = 0
			
			
			Game\Gameplay\NPCText$ = ""
			
		;	BASS_Stop : BASS_StreamFree(Stream) 
		;	BASS_Free()
			
			; STOP SOUNDS
			StopChannel(Channel_SpinDash)
			StopChannel(Channel_Glide)
			StopChannel(Channel_Fly)
			
			FreeSound(Sound_JumpVox1)
			FreeSound(Sound_JumpVox2)
			FreeSound(Sound_JumpVox3)
			FreeSound(Sound_HurtVox1)
			FreeSound(Sound_HurtVox2)
			FreeSound(Sound_HurtVox3)
			FreeSound(Sound_KickVox1)
			FreeSound(Sound_KickVox2)
			FreeSound(Sound_KickVox3)
			FreeSound(Sound_DashVox1)
			FreeSound(Sound_DashVox2)
			FreeSound(Sound_DashVox3)
			
			For i = 0 To SoundArrayEnd
			;	If Sounds[i]\Sound <> 0 Then Sounds[i]\Sound = 0
			;	If Sounds[i]\Sound <> 0 Then FreeSound Sounds[i]\Sound
			;	If Sounds[i]\Frame <> 0 Then Sounds[i]\Frame = 0
			;	If Sounds[i]\Anim <> 0 Then Sounds[i]\Anim = 0
			;	If Sounds[i]\Channel <> 0 Then Sounds[i]\Channel = 0
				Delete Sounds[i]
			Next
		
			Ready = 1
		
;		FreeEntity Game\Stage\Root
			
			If (Game\StartGame = 1 And Ready = 1) Then
				PlaySound Sound_SelectLevel
				HidePointer()
				ChannelVolume (Game\MusicChn, 0.4)
				Game\StageSelecting = 0
				Game\State = GAME_STATE_START
			Else
			
			Select Game\PickStage
					
				Case 1 : MakeMenu() : Game\State = GAME_STATE_SELECT1 : get = "" : Game\MusicChn = PlaySound(Sound_Menu) : ChannelVolume (Game\MusicChn, 1) : SetFont MenuFont : Game\TypeStage = 0 : Paused = 0
				;	b1.button = CreateTextButton ("Hub",		50,50,	100,20,	 1,	0)
					
				Case 0 : Game\State = GAME_STATE_START : SetFont SystemFont
			End Select
			
		EndIf
			
		
			
			
			
			
		;	FreeShadows()

	;	Game\State = GAME_STATE_END
		
	End Function


	; ---------------------------------------------------------------------------------------------------------
	; Game_Stage_UpdateProgressBar
	; ---------------------------------------------------------------------------------------------------------
	Function Game_Stage_UpdateProgressBar(Message$, t#)
		; Clear screen
		ClsColor(0,0,0)
		Cls()

		; Calculate percentage
		;Progression = Int(t#*396.0)
		Progression = Int(t#*396.0)

		; Draw message at center
		Color(255, 255, 255)
		
		Select Game\Stage\LoadMessage
			Case 0
				Text(GAME_WINDOW_W/2, GAME_WINDOW_H/2-95, "On your way to " + Game\Stage\Properties\Name$ + "...", True, False)
			Case 1
				Text(GAME_WINDOW_W/2, GAME_WINDOW_H/2-95, "Next stop: " + Game\Stage\Properties\Name$ + "!", True, False)
			Case 2
				Text(GAME_WINDOW_W/2, GAME_WINDOW_H/2-95, "Have fun at " + Game\Stage\Properties\Name$ + "!", True, False)
			Case 3
				Text(GAME_WINDOW_W/2, GAME_WINDOW_H/2-95, "Traveling to " + Game\Stage\Properties\Name$ + "...", True, False)
			Case 4
				Text(GAME_WINDOW_W/2, GAME_WINDOW_H/2-95, "Arriving at " + Game\Stage\Properties\Name$ + "...", True, False)
			Case 5
				Text(GAME_WINDOW_W/2, GAME_WINDOW_H/2-95, "Time to rock " + Game\Stage\Properties\Name$ + "!", True, False)
		End Select
		
		Text(GAME_WINDOW_W/2, GAME_WINDOW_H/2+130, Message$, True, False)
		Rect(GAME_WINDOW_W/2-170, GAME_WINDOW_H/2+40, 400, 50, False)
		Rect(GAME_WINDOW_W/2-168, GAME_WINDOW_H/2+42, Progression, 46, True)
		
	;	Rect(GAME_WINDOW_W/2-170, GAME_WINDOW_H/2+40, 400, 50, False)
	;	Rect(GAME_WINDOW_W/2-168, GAME_WINDOW_H/2+42, Progression, 46, True)
		
		; Original
	;	Progression = Int(t#*196.0)
	;	Text(GAME_WINDOW_W/2-100, GAME_WINDOW_H/2-40, Message$)
	;	Rect(GAME_WINDOW_W/2-100, GAME_WINDOW_H/2-25, 200, 50, False)
	;	Rect(GAME_WINDOW_W/2-98, GAME_WINDOW_H/2-23, Progression, 46, True)

		; Flip
		Flip(GAME_WINDOW_VSYNC)
End Function
	; ---------------------------------------------------------------------------------------------------------
	; Goal End
	; ---------------------------------------------------------------------------------------------------------
Function GoalEnd(p.tPlayer, d.tDeltaTime)
	
	
;	p\Motion\Speed\X# = 0
;	p\Motion\Speed\Z# = 0
	
	Game\StartGame = 0
	
	StopChannel Game\MusicChn
	
;	Repeat
		
;	ModInput(3)

;Game_Update()

If Game\EndTimer < MilliSecs() Then

If (Game\Stage\Ends = 0) Then
	Game\Pickstage = 0 : Game\Path$ = "Stages/" + Game\Stage\GoalDestination$ + "/" : Game\State=GAME_STATE_END
EndIf

If (Game\Stage\Ends = 1) Then
	Game\Pickstage = 1 : Game\State=GAME_STATE_END : Game\Gameplay\TypingStage = 1; : Game\Path$ = "Stages/" + Game\Stage\Hub$ + "/"
EndIf

Goal = 0

EndIf

End Function

;------
;MISC
;------
Global StageIsTilted=False
Function Stage_Action_Tilt(TransitionTime#, TiltDuration#, x#, y#, z#)
;prevx#=Game\Stage\GravityAlignment\x#
;If prevx#<>0.0 Then
	StageIsTilted=True
	;For i=0.0 To TansitionTime
	TiltX# = Sin(x)*Pi/360
	TiltY# = Sin(y)
	TiltZ# = Sin(z)
	Game\Stage\GravityAlignment\x# = -Sin(x)
		;Game\Stage\GravityAlignment\y# = -TiltY
		;Game\Stage\GravityAlignment\z# = -TiltZ
		;RotateEntity(Game\Stage\Properties\SkyBox, -Game\Stage\GravityAlignment\x#, -Game\Stage\GravityAlignment\y#, -Game\Stage\GravityAlignment\z#)
	
	For p.tPlayer = Each tPlayer
		p\Motion\GroundNormalAlign = Vector(Sin(x), 0, 0)
		
	;For p.tPlayer = Each tPlayer
		If p\Motion\GroundNormalAlign\x <> 0 Then
			For c.tCamera = Each tCamera
				c\Alignment\x = -Sin(x#)
			Next
		EndIf
	;Next
		
	Next
	
;	PlaySound Sound_Siren
	
		;StageIsTilted=False
;End If
End Function

Function ResetCheckPoint(p.tPlayer)
	CheckPointX# = 0
	CheckPointY# = 0
	CheckPointZ# = 0
End Function

;||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||
; mLoadRings places rings at child object locations within a mesh.
;||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||
Function mLoadRings(mesh)
    For i=1 To CountChildren(mesh)
		child = GetChild(mesh, i)
		
		positionX# = EntityX#(child, True)
		positionY# = EntityY#(child, True)
		positionZ# = EntityZ#(child, True)
		
		obj.tObject = Object_Ring_Create(positionX#, positionY#, positionZ#)
    Next
End Function

Function CreateStageLight1()
l.tGame_Lights = New tGame_Lights
	l\Light = CreateLight(1)
	Return l\Light
End Function

Function CreateStageLight2()
	l.tGame_Lights = New tGame_Lights
	l\Light = CreateLight(2)
	Return l\Light
End Function

Function CreateStageLight3()
	l.tGame_Lights = New tGame_Lights
	l\Light = CreateLight(3)
	Return l\Light
End Function

Function AnimateWater()
		; Animated water textures	
	For m.MeshStructure = Each MeshStructure
	
		If (m\WaterEntity <> 0) Then
		
		If Game\Stage\Properties\WaterTextureTimer<MilliSecs() Then
			EntityAlpha m\WaterEntity, 0.531
			Game\Stage\Properties\WaterTextureTimer=MilliSecs()+50
			Game\Stage\Properties\WaterTexture=Game\Stage\Properties\WaterTexture+1
			If Game\Stage\Properties\WaterTexture>13 Then Game\Stage\Properties\WaterTexture=1
			
			If Game\Stage\Properties\WaterTexture=1 Then
				EntityTexture m\WaterEntity, water1, 0,1
				
			ElseIf Game\Stage\Properties\WaterTexture=2 Then
				EntityTexture m\WaterEntity, water2, 0,1
				
			ElseIf Game\Stage\Properties\WaterTexture=3 Then
				EntityTexture m\WaterEntity, water3, 0,1
				
			ElseIf Game\Stage\Properties\WaterTexture=4 Then
				EntityTexture m\WaterEntity, water4, 0,1
				
			ElseIf Game\Stage\Properties\WaterTexture=5 Then
				EntityTexture m\WaterEntity, water5, 0,1
				
			ElseIf Game\Stage\Properties\WaterTexture=6 Then
				EntityTexture m\WaterEntity, water6, 0,1
				
			ElseIf Game\Stage\Properties\WaterTexture=7 Then
				EntityTexture m\WaterEntity, water7, 0,1
				
			ElseIf Game\Stage\Properties\WaterTexture=8 Then
				EntityTexture m\WaterEntity, water8, 0,1
				
			ElseIf Game\Stage\Properties\WaterTexture=9 Then
				EntityTexture m\WaterEntity, water9, 0,1
				
			ElseIf Game\Stage\Properties\WaterTexture=10 Then
				EntityTexture m\WaterEntity, water10, 0,1
				
			ElseIf Game\Stage\Properties\WaterTexture=11 Then
				EntityTexture m\WaterEntity, water11, 0,1
				
			ElseIf Game\Stage\Properties\WaterTexture=12 Then
				EntityTexture m\WaterEntity, water12, 0,1
				
			ElseIf Game\Stage\Properties\WaterTexture=13 Then
				EntityTexture m\WaterEntity, water13, 0,1
				
			EndIf
		EndIf
	EndIf
	
	
	If (m\LavaEntity <> 0) Then
		
		If Game\Stage\Properties\WaterTextureTimer<MilliSecs() Then
		;	EntityAlpha m\LavaEntity, 0.531
			Game\Stage\Properties\WaterTextureTimer=MilliSecs()+50
			Game\Stage\Properties\LavaTexture=Game\Stage\Properties\LavaTexture+1
			If Game\Stage\Properties\LavaTexture>13 Then Game\Stage\Properties\LavaTexture=1
			
			If Game\Stage\Properties\LavaTexture=1 Then
				EntityTexture m\LavaEntity, Lava1, 0,1
				
			ElseIf Game\Stage\Properties\LavaTexture=2 Then
				EntityTexture m\LavaEntity, Lava2, 0,1
				
			ElseIf Game\Stage\Properties\LavaTexture=3 Then
				EntityTexture m\LavaEntity, Lava3, 0,1
				
			ElseIf Game\Stage\Properties\LavaTexture=4 Then
				EntityTexture m\LavaEntity, Lava4, 0,1
				
			ElseIf Game\Stage\Properties\LavaTexture=5 Then
				EntityTexture m\LavaEntity, Lava5, 0,1
				
			ElseIf Game\Stage\Properties\LavaTexture=6 Then
				EntityTexture m\LavaEntity, Lava6, 0,1
				
			ElseIf Game\Stage\Properties\LavaTexture=7 Then
				EntityTexture m\LavaEntity, Lava7, 0,1
				
			ElseIf Game\Stage\Properties\LavaTexture=8 Then
				EntityTexture m\LavaEntity, Lava8, 0,1
				
			ElseIf Game\Stage\Properties\LavaTexture=9 Then
				EntityTexture m\LavaEntity, Lava9, 0,1
				
			ElseIf Game\Stage\Properties\LavaTexture=10 Then
				EntityTexture m\LavaEntity, Lava10, 0,1
				
			ElseIf Game\Stage\Properties\LavaTexture=11 Then
				EntityTexture m\LavaEntity, Lava11, 0,1
				
			ElseIf Game\Stage\Properties\LavaTexture=12 Then
				EntityTexture m\LavaEntity, Lava12, 0,1
				
			ElseIf Game\Stage\Properties\LavaTexture=13 Then
				EntityTexture m\LavaEntity, Lava13, 0,1
				
			EndIf
		EndIf
	EndIf
Next
		
	
    If (Game\Stage\Properties\Water <> 0) Then	
		If Game\Stage\Properties\WaterTextureTimer<MilliSecs() Then
			EntityAlpha Game\Stage\Properties\Water, 0.531
			Game\Stage\Properties\WaterTextureTimer=MilliSecs()+50
			Game\Stage\Properties\WaterTexture=Game\Stage\Properties\WaterTexture+1
			If Game\Stage\Properties\WaterTexture>13 Then Game\Stage\Properties\WaterTexture=1
			
			If Game\Stage\Properties\WaterTexture=1 Then
				EntityTexture Game\Stage\Properties\Water, water1, 0,1
				
			ElseIf Game\Stage\Properties\WaterTexture=2 Then
				EntityTexture Game\Stage\Properties\Water, water2, 0,1
				
			ElseIf Game\Stage\Properties\WaterTexture=3 Then
				EntityTexture Game\Stage\Properties\Water, water3, 0,1
				
			ElseIf Game\Stage\Properties\WaterTexture=4 Then
				EntityTexture Game\Stage\Properties\Water, water4, 0,1
				
			ElseIf Game\Stage\Properties\WaterTexture=5 Then
				EntityTexture Game\Stage\Properties\Water, water5, 0,1
				
			ElseIf Game\Stage\Properties\WaterTexture=6 Then
				EntityTexture Game\Stage\Properties\Water, water6, 0,1
				
			ElseIf Game\Stage\Properties\WaterTexture=7 Then
				EntityTexture Game\Stage\Properties\Water, water7, 0,1
				
			ElseIf Game\Stage\Properties\WaterTexture=8 Then
				EntityTexture Game\Stage\Properties\Water, water8, 0,1
				
			ElseIf Game\Stage\Properties\WaterTexture=9 Then
				EntityTexture Game\Stage\Properties\Water, water9, 0,1
				
			ElseIf Game\Stage\Properties\WaterTexture=10 Then
				EntityTexture Game\Stage\Properties\Water, water10, 0,1
				
			ElseIf Game\Stage\Properties\WaterTexture=11 Then
				EntityTexture Game\Stage\Properties\Water, water11, 0,1
				
			ElseIf Game\Stage\Properties\WaterTexture=12 Then
				EntityTexture Game\Stage\Properties\Water, water12, 0,1
				
			ElseIf Game\Stage\Properties\WaterTexture=13 Then
				EntityTexture Game\Stage\Properties\Water, water13, 0,1
				
			EndIf
		EndIf
		
	EndIf
End Function

	; ---------------------------------------------------------------------------------------------------------
	; Handle Online Packets
	; ---------------------------------------------------------------------------------------------------------

Function HandleMessages ()
	For msg.MsgInfo = Each MsgInfo
		Select msg\msgType
			Case 1		;Player 2 X Position
				Player2PositionX = (msg\msgData)
			Case 2		;Player 2 Y Position
				Player2PositionY = (msg\msgData)
			Case 3		;Player 2 Z Position
				Player2PositionZ = (msg\msgData)
			Case 4		;Player 2 X Rotation
				Player2RotationX = (msg\msgData)
			Case 5		;Player 2 Y Rotation
				Player2RotationY = (msg\msgData)
			Case 6		;Player 2 Z Rotation
				Player2RotationZ = (msg\msgData)
			Case 7		;Player 2 Animation
				Player2Animation = (msg\msgData)
				
		End Select
		Delete msg
	Next
	
End Function

Function LabelEntity (camera, entity, label$)
	If EntityInView (entity, camera)
		CameraProject camera, EntityX (entity), EntityY (entity), EntityZ (entity)
		w = StringWidth (label$)
		h = StringHeight (label$)
		x = ProjectedX () - (w / 2) - 1
		y = ProjectedY () - (h / 2) - 1
		Color 0, 0, 0
		Rect x, y-40, w + 2, h + 2, 1
		Color 255, 255, 255
		Text x, y-40, label$
	EndIf
End Function
;~IDEal Editor Parameters:
;~C#Blitz3D