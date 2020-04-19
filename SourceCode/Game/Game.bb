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
; - Héctor "Damizean" (elgigantedeyeso at gmail dot com)
; - Mark "Coré" (mabc_bh at yahoo dot com dot br)
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
;               17/01/2008 - Code reorganization.                                                              ;
;                                                                                                              ;
;==============================================================================================================;
;                                                                                                              ;
;   TODO:                                                                                                      ;
;                                                                                                              ;
;==============================================================================================================;

; /\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/
; 	STRUCTURES
; /\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/

	; ---------------------------------------------------------------------------------------------------------
	; tGame
	; ---------------------------------------------------------------------------------------------------------
	Type tGame
		Field	DeltaTime.tDeltaTime
		Field	Gameplay.tGame_Gameplay

		Field	Menu.tMenu_Machine
		Field	Stage.tGame_Stage
		
		Field	Others.tGame_Others
		Field	Mode, NewMode, State
		Field	Lights.tGame_Lights[999]
		Field	LightNumber
		Field	Path$
		Field	PickStage
		Field	MusicChn
		Field	Online
		Field	Player1
		Field	Player2
		Field	StageSelecting
		Field	TypeStage
		Field	Transitioning
		Field	StageError
		Field	StartGame
		
		Field	SystemFont
		Field	GameFont
		Field	CreatedMenu
		Field	CreatedCharacter
		
		Field	EndTimer
		
	;	Field	ParticleGroup1.tParticleGroup
	;	Field	ParticleGroup2.tParticleGroup
	;	Field	ParticleGroup3.tParticleGroup
	;	Field	ParticleGroup4.tParticleGroup
		
		
	End Type

	; ---------------------------------------------------------------------------------------------------------
	; tGame_Gameplay
	; ---------------------------------------------------------------------------------------------------------
	Type tGame_Gameplay
		Field	Character
		Field 	Lives
		
		Field	Score
		Field 	Time
		Field 	Rings
		
		Field	TimeResult
		
		Field	NPCText$
		Field	NPCTextTimer
		Field	NPCTalking
		
		Field	TypingStage
		Field	Rot
		Field	HintTime
		
		; Footstep
		Field Foot_Walk
		Field Foot_Jog1
		Field Foot_Jog2
		Field Foot_Run
		Field Foot_Sprint
		
		Field Foot_WFrame1
		Field Foot_WFrame2
		
		Field Foot_J1Frame1
		Field Foot_J1Frame2
		
		Field Foot_J2Frame1
		Field Foot_J2Frame2
		
		Field Foot_R1Frame1
		Field Foot_R1Frame2
		
		Field Foot_R2Frame1
		Field Foot_R2Frame2
	End Type

	; ---------------------------------------------------------------------------------------------------------
	; tGame_StagesList
	; ---------------------------------------------------------------------------------------------------------
	Type tGame_StagesList
		Field	Folder1$
		Field	Folder2$
		Field	Folder3$
		Field	Folder4$
		Field	Folder5$
		Field	Folder6$
		Field	Folder7$
		Field	Folder8$
		Field	Folder9$
		Field	Folder10$
	End Type

	; ---------------------------------------------------------------------------------------------------------
	; tGame_Stage
	; ---------------------------------------------------------------------------------------------------------
	Type tGame_Stage
		Field	Properties.tGame_StageProperties
		Field 	List.tGame_StagesList
		Field	Root
		Field	Yn$
		Field	Hub$
		Field	Gravity
		Field 	GravityAlignment.tVector
		Field	LoadMessage
		
		Field	Ends
		Field	GoalDestination$
		
		Field	StartX#
		Field	StartY#
		Field	StartZ#
		Field	StartPos
		Field	StartPosDel
		
		Field	StartAction
		
		Field	DefStartX#
		Field	DefStartY#
		Field	DefStartZ#
		
	;	Field	Rain
	;	Field	Snow
	End Type

	; ---------------------------------------------------------------------------------------------------------
	; tGame_StageProperties
	; ---------------------------------------------------------------------------------------------------------
	Type tGame_StageProperties
		Field	Name$
		Field 	Music
		Field	MusicLoops
		Field	StartMusic
		Field   SkyBox
		Field   Water
		Field	Light
		Field	WaterFx.FxMeshPostProcess
		Field	WaterTexture
		Field	LavaTexture
		Field	WaterDistortion
		Field   WaterLevel
	;	Field	Rain
	;	Field	Snow
		Field	Hub
		Field	Sun
		
	; Fog
		Field	FogAmmountNear#, FogAmmountFar#
		Field	FogColorR, FogColorG, FogColorB
	; Lava
		Field   Lava
		Field	LavaAnimTexture
		Field   LavaLevel
		
		Field	WaterTextureTimer
	End Type

	; ---------------------------------------------------------------------------------------------------------
	; tGame_Others
	; ---------------------------------------------------------------------------------------------------------
	Type tGame_Others
		Field 	Fps%
		Field 	Frames%
		Field 	NextFrame%
		Field	Explode
	End Type 
	
	; ---------------------------------------------------------------------------------------------------------
	; tGame_Lights
	; ---------------------------------------------------------------------------------------------------------
	Type tGame_Lights
	;	Field Point
	;	Field Spot
	;	Field Dir
		Field Light
	End Type
	
	; ---------------------------------------------------------------------------------------------------------
	; tGame_Sounds
	; ---------------------------------------------------------------------------------------------------------
	Type tGame_Sounds
		Field LoadedSound[99]
	End Type
	
	
	; ---------------------------------------------------------------------------------------------------------
	; OnlineStuff
	; ---------------------------------------------------------------------------------------------------------
	Type Info1
		Field txt$
	End Type
	
;	Type tChat
;		Field Allowed
;	End Type
;	chat.tChat=New tChat
	
; /\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/
; 	CONSTANTS
; /\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/
	
	Global StageEnding = 0
	Global ListenerCreated = 0
	Global SoundLoaded$
	Global SonicCam
	Global MBlur
	Global GlobalTimer
	Global GlobalTimerFloat#
	Global RootNode
	Global OverlayTimer#
	Global BlastEffectTimer#
	Global BetaTapeMode
	Global WherePlayers
	Global GameStarted
	Global SoundsLoaded
	Global HomeButton
	
	Global ChatTimer
	Global SentMessage
	;Global get = ""
	;Global gg
	
	;First, Create a camera specially for the cube
;	Global CubeCamera = CreateCamera()
;	CameraRange(CubeCamera, 		0.1, 999999999)
;	CameraProjMode CubeCamera, 0 ;Setting ProjectionMode to 0 deactives the camera
	
;Second, Create a texture for render the camera
;	Global CubeSize = 256 ; When bigger the size, bigger the quality, bigger the lag
;	Global Tex = CreateTexture(CubeSize, CubeSize,1+128+256)
	
;Now you need a mesh for receive the texture, somehow planes doesn't work well, I wish they could... Give it a try later and you will see!
;	Global WaterSize = 150
;	Global SizeMultiplyer = 200
;	Global Water
	
	; --- Trail ---
;	Const trail_update=1				; trail update frequency (lower number updates more often)
;	Const maxVerts=100					; maximum number of verts per trail object (polygons * 2)
	
	; --- Game Modes ----
	Const		GAME_MODE_STARTUP	=	0
	Const 		GAME_MODE_MENU		=	1
	Const 		GAME_MODE_STAGE		=	2

	; --- Stage Modes ----
	Const 		GAME_STATE_START	=	0
	Const 		GAME_STATE_STEP		=	1
	Const 		GAME_STATE_END		=	2
	Const 		GAME_STATE_SELECT	=	3
	Const 		GAME_STATE_SELECT1	=	4

	; --- World constants ---
	Const		GAME_SCALE#			=	0.1
	
	; --- GUI ---
	Global edt1
	Global ButtonGo
	Global ButtonExit
	Global Yn$
;	Global DebugEnabled
	
	Global Debug
	Global HeadTrackingEnabled
	Global BBlastEnabled
	Global FootStepEnabled
	Global TiltingEnabled
	Global FXEnabled
	Global UseStageChar
	Global CharType
	
	Global	CamPosX#
	Global	CamPosY#
	Global	CamPosZ#
	
	Global	CamRotX#
	Global	CamRotY#
	Global	CamRotZ#
	
	Global	CamRadius#
	
	Global StrLevel
	
	Global ShowHUD = 1
;	Global FirstShadowTexture
;	Global Light
	
;	Global Read1$
;	Global Read2$
;	Global Read3$
;	Global Read4$
	
	Global Goal
	
	Global PlayerCollisions
	
	Global IP$
	
	Global Chatting
	
;	Global Win
;	Global WinMenu
;	Global grp10
;	Global edit1
	
	Global Paused = 0
	
	Global MenuFont=LoadFont("Arial",12,True,False,False)
	Global SystemFont=LoadFont("Arial",30,True,False,False)
	Global GameFont=LoadFont("Arial",22,True,False,False)
	
	SetFont MenuFont
	
; /\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/
; 	METHODS
; /\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/

	; ---------------------------------------------------------------------------------------------------------
	; Game_Startup
	; ---------------------------------------------------------------------------------------------------------
	Function Game_Startup()
		; Set game title
		FxManager_Startup()
		InitDraw() : AppTitle(GAME_TITLE$)
		WindowHWND = SystemProperty("AppHWND")
		;SetBuffer(BackBuffer())
		;InitDraw(SystemProperty("Direct3DDevice7"))
		
		ShowHUD = 1
		
		
		Game\Stage\Properties\MusicLoops = 1
		
		; Startup gameplay values
		;Game\PickStage			= 1
	;	SetFont SystemFont
	;	Game\Path$	= "Stages/Test3/"
		Game\State = GAME_STATE_SELECT1 : Game\CreatedMenu = 0
		HidePointer()
		ChannelVolume (Game\MusicChn, 0.4)
		Game\StageSelecting = 1
		
		Game\Gameplay\Character = CHARACTER_SONIC
		Game\Gameplay\Lives		= 3
		Game\Gameplay\Score		= 0
		Game\Gameplay\Time		= 0
		Game\Gameplay\Rings		= 0
		Game\Stage\Yn$			= 0
		Game\PickStage 			= 1
		
	;	Skin					= GUI_LoadSkin("Skins\BackRow.skin")
		
		Game\MusicChn = PlaySound(Sound_Menu)
		ChannelVolume (Game\MusicChn, 1)
	;	b1.button = CreateTextButton ("Hub",		50,50,	100,20,	 1,	0)
		
					; GUI
;		GUI_InitGUI("Skins\BackRow.skin")
;		winMain = GUI_CreateWindow(x, y, 420, 395, "Stage Select", "Gfx\WindowIcon.png", False, False, False, False)
;		edt1 = GUI_CreateEdit(winMain, 10, 13, 100, "")
;		ButtonGo = GUI_CreateButton(winMain, 10, 50, 75, 25, "Go")
;		ButtonExit = GUI_CreateButton(winMain, 10, 90, 75, 25, "Exit")
		
	;	Game\Gameplay\TypingStage = 1
	;	Game\Path$ = ""

						;Load stagelist
		StageListRoot = xmlLoad("Stages/Stages.xml")
		If (xmlErrorCount()>0) Then RuntimeError("You may have set something up wrong in 'Stages.xml' located in the 'Stages' folder")
		For i=1 To xmlNodeChildCount(StageListRoot)
			Child = xmlNodeChild(StageListRoot, i)
			If (xmlNodeNameGet(Child)="stage") Then
				stg.tGame_StagesList = New tGame_StagesList
				stg\Folder1$ = xmlNodeAttributeValueGet(Child, "folder1")
				stg\Folder2$ = xmlNodeAttributeValueGet(Child, "folder2")
				stg\Folder3$ = xmlNodeAttributeValueGet(Child, "folder3")
				stg\Folder4$ = xmlNodeAttributeValueGet(Child, "folder4")
				stg\Folder5$ = xmlNodeAttributeValueGet(Child, "folder5")
				stg\Folder6$ = xmlNodeAttributeValueGet(Child, "folder6")
				stg\Folder7$ = xmlNodeAttributeValueGet(Child, "folder7")
				stg\Folder8$ = xmlNodeAttributeValueGet(Child, "folder8")
				stg\Folder9$ = xmlNodeAttributeValueGet(Child, "folder9")
				stg\Folder10$ = xmlNodeAttributeValueGet(Child, "folder10")
				
			End If 
		Next
		xmlNodeDelete(StageListRoot)
		
		;Bass_Start()
		
	End Function
	
Function SetUpMenu()
	GUI_InitGUI("Skins\BackRow.skin")
	winMain = GUI_CreateWindow(x, y, 420, 395, "Stage Select", "Gfx\WindowIcon.png", False, False, False, False)
	edt1 = GUI_CreateEdit(winMain, 10, 13, 100, "")
	ButtonGo = GUI_CreateButton(winMain, 10, 50, 75, 25, "Go")
	ButtonExit = GUI_CreateButton(winMain, 10, 90, 75, 25, "Exit")
End Function

	; ---------------------------------------------------------------------------------------------------------
	; Game_Update
	; ---------------------------------------------------------------------------------------------------------
	Function Game_Update()
		; Acquire keyboard and controls status and update delta time
		DeltaTime_Update(Game\DeltaTime)
		Input_Update()
		
		; Depending on current game's
		Select Game\Mode
			Case GAME_MODE_STARTUP
				Game_Startup_Update()
			Case GAME_MODE_MENU
				Game_Menu_Update()
			Case GAME_MODE_STAGE
				Game_Stage_Update()
		End Select
	End Function


	; ---------------------------------------------------------------------------------------------------------
	; Game_Startup
	; ---------------------------------------------------------------------------------------------------------
	Function Game_Startup_Update()
		; On this game Mode, we just switch Mode and set to start State.
		Game\Mode	= GAME_MODE_STAGE
		If Game\PickStage = 1 Then Game\State	= GAME_STATE_SELECT
		If Game\PickStage = 0 Then Game\State	= GAME_STATE_START
		Game\Stage\List = First tGame_StagesList
	End Function 

	
	; ---------------------------------------------------------------------------------------------------------
	; Game_End
	; ---------------------------------------------------------------------------------------------------------
Function Game_End()
;	Game\TypeStage = 0
;	PlaySound (Sound_SelectLevel) : StopNetGame() : Game\Stage\StartPos = 0 : Game\Stage\StartPosDel = 1 : Paused = 0 : Game\PickStage = 1 : Game\State = GAME_STATE_SELECT
	
	End Function

	; =========================================================================================================
	; FxManager_RenderingStepInterruption
	; =========================================================================================================
	Function FxManager_RenderingPassInterruption(Pass, Method)
		; Put this on your game. Before rendering anything, this function will be called by the
		; Render World method, so you can disable certain entities.
		If (Method = 0) Then
			Select Pass
				Case 1
					ShowEntity(Game\Stage\Properties\SkyBox)
					If (Game\Stage\Properties\Sun<>0) Then ShowEntity(Game\Stage\Properties\Sun)
				Case 2
					HideEntity(Game\Stage\Properties\SkyBox)
					If (Game\Stage\Properties\Sun<>0) Then ShowEntity(Game\Stage\Properties\Sun)
				Case 3
				Case 4
				Case 5
			End Select
		Else
			Select Pass
				Case 1
					ShowEntity(Game\Stage\Properties\SkyBox)
					If (Game\Stage\Properties\Sun<>0) Then ShowEntity(Game\Stage\Properties\Sun)
				Case 2
					HideEntity(Game\Stage\Properties\SkyBox)
					If (Game\Stage\Properties\Sun<>0) Then HideEntity(Game\Stage\Properties\Sun)
				Case 3
				Case 4
				Case 5
			End Select
		End If
End Function

Function RefreshStages()
							;Load stagelist
	StageListRoot = xmlLoad("Stages/Stages.xml")
	If (xmlErrorCount()>0) Then RuntimeError("You may have set something up wrong in 'Stages.xml' located in the 'Stages' folder")
	For i=1 To xmlNodeChildCount(StageListRoot)
		Child = xmlNodeChild(StageListRoot, i)
		If (xmlNodeNameGet(Child)="stage") Then
			For stg.tGame_StagesList = Each tGame_StagesList
			stg\Folder1$ = xmlNodeAttributeValueGet(Child, "folder1")
			stg\Folder2$ = xmlNodeAttributeValueGet(Child, "folder2")
			stg\Folder3$ = xmlNodeAttributeValueGet(Child, "folder3")
			stg\Folder4$ = xmlNodeAttributeValueGet(Child, "folder4")
			stg\Folder5$ = xmlNodeAttributeValueGet(Child, "folder5")
			stg\Folder6$ = xmlNodeAttributeValueGet(Child, "folder6")
			stg\Folder7$ = xmlNodeAttributeValueGet(Child, "folder7")
			stg\Folder8$ = xmlNodeAttributeValueGet(Child, "folder8")
			stg\Folder9$ = xmlNodeAttributeValueGet(Child, "folder9")
			stg\Folder10$ = xmlNodeAttributeValueGet(Child, "folder10")
		Next
		End If 
	Next
	xmlNodeDelete(StageListRoot)
	
	Game_State = GAME_STATE_SELECT1
	
End Function
	
; /\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/
; 	DECLARATIONS
; /\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/

	Global Game.tGame 			= New tGame
	Game\DeltaTime				= DeltaTime_Create()
	Game\Gameplay 				= New tGame_Gameplay
	Game\Stage					= New tGame_Stage
	Game\Stage\Properties		= New tGame_StageProperties
	Game\Others					= New tGame_Others
	;Game\Lights					= New tGame_Lights
;~IDEal Editor Parameters:
;~C#Blitz3D