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
; 	METHODS
; /\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/

	; ---------------------------------------------------------------------------------------------------------
	; Interface_Render
	; ---------------------------------------------------------------------------------------------------------
Function Interface_Render()
	
	If (KeyHit(KEY_F4)) Then ShowHUD = 1-ShowHUD
	
	
	StartDraw()
	
	
	
	
	
	
			; Setup rendering methods
			SetBlend(FI_ALPHABLEND)
			SetAlpha(1.0)
			SetScale(GAME_WINDOW_SCALE#, GAME_WINDOW_SCALE#)
			SetColor(255, 255, 255)
			
			If Goal = 1 Then
				DrawImageEx(Interface_GoalDisplay, GAME_WINDOW_W Shr 3.35, GAME_WINDOW_H Shr 2)
				Interface_Number(Game\Gameplay\Rings, 500*GAME_WINDOW_SCALE#, 138*GAME_WINDOW_SCALE#, 0, 1)
				Interface_Number((Game\Gameplay\TimeResult/1000) Mod 60, 562*GAME_WINDOW_SCALE#, 208*GAME_WINDOW_SCALE#, 0, 1)
				Interface_Number((Game\Gameplay\TimeResult/60000), 500*GAME_WINDOW_SCALE#, 208*GAME_WINDOW_SCALE#, 0, 1)
				Interface_Number(Game\Gameplay\Score, 500*GAME_WINDOW_SCALE#, 288*GAME_WINDOW_SCALE#, 0, 1)
			Else
			
				If ShowHUD = 1 Then
			
			; Render Score/Time/Rings image
			DrawImageEx(Interface_ScoreTimeRings, 32*GAME_WINDOW_SCALE#, 32*GAME_WINDOW_SCALE#)
			DrawImageEx(Interface_Icons, 32*GAME_WINDOW_SCALE#, GAME_WINDOW_H-85.0*GAME_WINDOW_SCALE#)
			
			
			
			; Render numbers
			For p.tPlayer = Each tPlayer
				For c.tCamera = Each tCamera
			Interface_Number(Game\Gameplay\Score, 				320*GAME_WINDOW_SCALE#, 30*GAME_WINDOW_SCALE#,		0, 1)
			Interface_Number((Game\Gameplay\Time/1000) Mod 60,  177*GAME_WINDOW_SCALE#, 59*GAME_WINDOW_SCALE#, 		2)
			Interface_Number((Game\Gameplay\Time/60000), 		145*GAME_WINDOW_SCALE#, 59*GAME_WINDOW_SCALE#, 		1)
			Interface_Number(Game\Gameplay\Rings, 				210*GAME_WINDOW_SCALE#, 88*GAME_WINDOW_SCALE#, 		0, 1)
			Interface_Number(Game\Gameplay\Lives, 				140*GAME_WINDOW_SCALE#, GAME_WINDOW_H-75.0*GAME_WINDOW_SCALE#, 	0, 1)
		;	Interface_Number(p\Motion\TiltAmount#,			 				140*GAME_WINDOW_SCALE#, GAME_WINDOW_H-35.0*GAME_WINDOW_SCALE#, 	0, 1)
			
		;	Interface_Text("Sonic", 105*GAME_WINDOW_SCALE#, 89*GAME_WINDOW_SCALE#, 0, 0, "Sans", 23)

			; Render FPS
			Interface_Number(Game\Others\FPS,					500*GAME_WINDOW_SCALE#, 30*GAME_WINDOW_SCALE#,		0, 1)
		Next
	Next
EndIf

	; If on pause mode, render pause
If (Paused = True And ShowHUD = 1) Then
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
	
	DrawImageEx(Interface_Pause, GAME_WINDOW_W Shr 1, GAME_WINDOW_H Shr 1)
End If
	
EndIf
EndDraw()


textcounter = textcounter + 10		
If Online = 1 Then
	
;	Color 0,0,0 : Rect 0, 300, 450, 370
;	Color 0,200,50 : Text 0,FontHeight()*textcounter, ">" + PlayerChatText$ + "_"
	Color 0,200,50 : Text 0,300, ">" + PlayerChatText$ + "_"
	
EndIf
textcounter = textcounter + 5
counter = 0
;Color 150,150,150
Color 0,255,50
For i.Info = Each Info
	counter = counter + 1
	If counter > 15 Then
		Delete i
	Else
		Text 0,FontHeight()*textcounter, i\txt
		textcounter = textcounter + 1
	End If
Next

;If Chatting>0 Then
;	AboutToChat()
;EndIf
			
	End Function

	; ---------------------------------------------------------------------------------------------------------
	; Interface_Number
	; ---------------------------------------------------------------------------------------------------------
	Function Interface_Number(Number%, x, y, ZeroPadding=0, Alignment=0)
		; Convert number to string
		Num$ = ZeroPadding$(Str$(Number%), ZeroPadding)

		If (Alignment=1) Then x = x-Len(Num$)*18*GAME_WINDOW_SCALE#
		
		; Go on and render text
		For i = 1 To Len(Num$)
			DrawImageEx(Interface_Numbers, x, y,  Asc(Mid$(Num$, i, 1))-48)
			x = x+21*GAME_WINDOW_SCALE#
		Next
End Function

	; ---------------------------------------------------------------------------------------------------------
	; Interface_Text
	; ---------------------------------------------------------------------------------------------------------
Function Interface_Text(Txt$, x, y, ZeroPadding=0, Alignment=0, Font=0, Width=23)
	If Font=0 Then Font=Interface_Font
		; Convert number to string
	T$ = ZeroPadding$(Txt$, ZeroPadding)
	
	If (Alignment=1) Then x = x-Len(T$)*18*GAME_WINDOW_SCALE#
	
		; Go on and render text
	For i = 1 To Len(T$)
		DrawImageEx(Font, x, y,  Asc(Mid$(T$, i, 1))-32)
		x = x+Width*GAME_WINDOW_SCALE#
	Next
End Function
;~IDEal Editor Parameters:
;~C#Blitz3D