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
	; tPostEffects
	; ---------------------------------------------------------------------------------------------------------
	Type tPostEffect_Fade
		Field Kind
		Field Alpha#
		Field Rate#
		Field R, G, B
	End Type

	Type tPostEffect_Blur
		Field Radius
		Field Skip
		Field Rate#
	End Type

	Type tPostEffect_MotionBlur
		Field Rate#
	End Type 
	
; /\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/
; 	CONSTANTS
; /\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/

	; ---- Effects kinds ----
	Const 		EFFECT_FADE_IN			=	0
	Const 		EFFECT_FADE_OUT			=	1
	Const		EFFECT_BLUR				=	2
	Const 		EFFECT_MOTIONBLUR		=	3


; /\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/
; 	VARIABLES
; /\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/

	; ---- Screen images ---
	Global 		PostScreen_Created = False
	Global		PostScreen_Updated = False

	Global		PostScreen_Width	= 0
	Global 		PostScreen_Height	= 0
	Global		PostScreen_TexSize	= Effects_TexSize
	
	Dim			PostScreen_Textures(1)
	Dim 		PostScreen_Images(1)
	

; /\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/
; 	METHODS
; /\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/

	; ---------------------------------------------------------------------------------------------------------
	; PostEffect_UpdateAll
	; ---------------------------------------------------------------------------------------------------------
	Function PostEffect_UpdateAll(d.tDeltaTime)
		; Reset screen captured flag
		PostScreen_Updated = False

		; Update effects
		For f0.tPostEffect_Fade = Each tPostEffect_Fade 			: PostEffect_Update_Fade(f0, d) 		: Next
		For f1.tPostEffect_MotionBlur = Each tPostEffect_MotionBlur : PostEffect_Update_MotionBlur(f1, d) 	: Next
		For f2.tPostEffect_Blur = Each tPostEffect_Blur 			: PostEffect_Update_Blur(f2, d) 		: Next
	End Function


	; ---------------------------------------------------------------------------------------------------------
	; PostEffect_UpdateScreen
	; ---------------------------------------------------------------------------------------------------------
	Function PostEffect_UpdateScreen()
		; If the screen pieces haven't been created yet, do it
		If (PostScreen_Created = False) Then
			; Separate the screen in width and height pieces
			PostScreen_Width  = Int(Ceil#(Float#(GAME_WINDOW_W) / Float#(PostScreen_TexSize)))
			PostScreen_Height = Int(Ceil#(Float#(GAME_WINDOW_H) / Float#(PostScreen_TexSize)))
			
			; Redimensaionate the textures and images buffer
			Dim PostScreen_Textures(PostScreen_Width*PostScreen_Height)
			Dim PostScreen_Images(PostScreen_Width*PostScreen_Height)

			; Create textures and images
			For y = 0 To PostScreen_Height-1
				For x = 0 To PostScreen_Width-1
					PostScreen_Textures(x+y*PostScreen_Width) = CreateTexture(PostScreen_TexSize, PostScreen_TexSize, 1+256)
					PostScreen_Images(x+y*PostScreen_Width)   = CreateImageEx(PostScreen_Textures(x+y*PostScreen_Width), PostScreen_TexSize, PostScreen_TexSize, FI_NONE)
				Next
			Next

			; Done
			PostScreen_Created = True
		End If
		
		; Update if not updated yet
		If (PostScreen_Updated = False) Then
			; Recover textures and recreate images
			For y = 0 To PostScreen_Height-1
				For x = 0 To PostScreen_Width-1
					FreeImageEx(PostScreen_Images(x+y*PostScreen_Width))
					CopyRect(x*PostScreen_TexSize, y*PostScreen_TexSize, PostScreen_TexSize, PostScreen_TexSize, 0, 0, BackBuffer(), TextureBuffer(PostScreen_Textures(x+y*PostScreen_Width)))
					PostScreen_Images(x+y*PostScreen_Width)   = CreateImageEx(PostScreen_Textures(x+y*PostScreen_Width), PostScreen_TexSize, PostScreen_TexSize, FI_NONE)
				Next
			Next

			; Done
			PostScreen_Updated = True
		End If
	End Function


	; ---------------------------------------------------------------------------------------------------------
	; PostEffect_RenderScreen
	; ---------------------------------------------------------------------------------------------------------
	Function PostEffect_RenderScreen(dx, dy)
		For y = 0 To PostScreen_Height-1
			For x = 0 To PostScreen_Width-1
				DrawImageEx(PostScreen_Images(x+y*PostScreen_Width), dx+x*PostScreen_TexSize, dy+y*PostScreen_TexSize)
			Next
		Next
	End Function


	; ---- Creation ----
	; ---------------------------------------------------------------------------------------------------------
	; PostEffect_Create_FadeIn
	; ---------------------------------------------------------------------------------------------------------
	Function PostEffect_Create_FadeIn.tPostEffect_Fade(Rate#, R=0, G=0, B=0)
		f.tPostEffect_Fade = New tPostEffect_Fade
			f\Kind 		= 0
			f\Alpha#	= 1.0
			f\Rate#		= Rate#
			f\R			= R
			f\G			= G
			f\B			= B
		Return f
	End Function 


	; ---------------------------------------------------------------------------------------------------------
	; PostEffect_Create_FadeOut
	; ---------------------------------------------------------------------------------------------------------
	Function PostEffect_Create_FadeOut.tPostEffect_Fade(Rate#, R=0, G=0, B=0)
		f.tPostEffect_Fade = New tPostEffect_Fade
			f\Kind 		= 1
			f\Alpha#	= 0.0
			f\Rate#		= Rate#
			f\R			= R
			f\G			= G
			f\B			= B
		Return f
	End Function


	; ---------------------------------------------------------------------------------------------------------
	; PostEffect_Create_Blur
	; ---------------------------------------------------------------------------------------------------------
	Function PostEffect_Create_Blur.tPostEffect_Blur(Radius, Skip=1)
		If (Effects_Blur = False) Then Return Null
		
		f.tPostEffect_Blur = New tPostEffect_Blur
			f\Radius = Radius
			f\Rate#	 = 1/Float#(Radius)
			f\Skip	 = Skip
		Return f
	End Function


	; ---------------------------------------------------------------------------------------------------------
	; PostEffect_Create_MotionBlur
	; ---------------------------------------------------------------------------------------------------------
	Function PostEffect_Create_MotionBlur.tPostEffect_MotionBlur(Rate#)
		If (Effects_Blur = False) Then Return Null
		
		f.tPostEffect_MotionBlur = New tPostEffect_MotionBlur
			f\Rate# = Rate#
		PostEffect_UpdateScreen()
		Return f
	End Function


	; ---- Update ----
	; ---------------------------------------------------------------------------------------------------------
	; PostEffect_Update_Fade
	; ---------------------------------------------------------------------------------------------------------
	Function PostEffect_Update_Fade(f.tPostEffect_Fade, d.tDeltaTime)
		; Render
		StartDraw()
			; Setup rendering methods
			SetBlend(FI_ALPHABLEND)
			SetAlpha(f\Alpha#)
			SetScale(1, 1)
			SetColor(f\R, f\G, f\B)

			; Render rect
			DrawRect(0, 0, GAME_WINDOW_W, GAME_WINDOW_H, 1)
		EndDraw()
		
		; Manage
		Select f\Kind
			Case 0
				f\Alpha# = f\Alpha#-f\Rate#*d\Delta#
				If (f\Alpha#<=0.0) Delete f
			Case 1
				f\Alpha# = f\Alpha#+f\Rate#*d\Delta#
				If (f\Alpha#>=1.0) Delete f
		End Select
	End Function


	; ---------------------------------------------------------------------------------------------------------
	; PostEffect_Update_Blur
	; ---------------------------------------------------------------------------------------------------------
	Function PostEffect_Update_Blur(f.tPostEffect_Blur, d.tDeltaTime)
		PostEffect_UpdateScreen()
		StartDraw()
			; Setup rendering methods
			SetBlend(FI_ALPHABLEND)
			SetAlpha(f\Rate#)
			SetScale(1, 1)
			SetColor(255, 255, 255)

			; Render screen
			x = -f\Radius
			While(x<f\Radius)
				y = -f\Radius
				While(y<f\Radius)
					PostEffect_RenderScreen(x, y)
					y = y+f\Skip
				Wend
				x = x+f\Skip
			Wend 
		EndDraw()
	End Function


	; ---------------------------------------------------------------------------------------------------------
	; PostEffect_Update_MotionBlur
	; ---------------------------------------------------------------------------------------------------------
	Function PostEffect_Update_MotionBlur(f.tPostEffect_MotionBlur, d.tDeltaTime)
		StartDraw()
			; Setup rendering methods
			SetBlend(FI_ALPHABLEND)
			SetAlpha(f\Rate#)
			SetScale(1.007, 1.007)
			SetColor(255, 255, 255)
			
			f\Rate# = f\Rate# - 0.06 * d\Delta			

			; Render screen
			PostEffect_RenderScreen(-GAME_WINDOW_W*0.0025, -GAME_WINDOW_H*0.0025)
		EndDraw()
		PostEffect_UpdateScreen()
		
		If f\Rate < 0.1 Then Delete f: Return		
	End Function