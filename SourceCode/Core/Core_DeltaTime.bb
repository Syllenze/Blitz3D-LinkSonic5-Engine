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
	; tDeltaTime
	; ---------------------------------------------------------------------------------------------------------
	Type tDeltaTime
		; Ideal values
		Field IdealFPS
		Field IdealInterval#

		; Intervals
		Field TimePreviousFrame
		Field TimeCurrentFrame

		; Delta values
		Field DeltaList#[20]
		Field DeltaNode
		Field Delta#
		Field Time#
		Field InvDelta#
		
		; Custom
		Field Subtract#
	End Type	

; /\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/
; 	METHODS
; /\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/

	Function DeltaTime_Create.tDeltaTime(FPS=60)
		d.tDeltaTime = New tDeltaTime
		d\IdealFPS 			= FPS
		d\IdealInterval		= 1/(1000/Float(FPS)) ;1/(1000/Float(FPS)) Change the second value to achieve FAST or SLOW motion!
		d\TimePreviousFrame = MilliSecs()
		d\TimeCurrentFrame	= MilliSecs()
		Return d
	End Function

	Function DeltaTime_Reset(d.tDeltaTime)
		d\TimePreviousFrame = MilliSecs()
		d\TimeCurrentFrame = MilliSecs(); - d\Subtract#
		d\Delta# = 0.0
		d\DeltaNode = 0
		For i=0 To 19
			d\DeltaList#[i] = 0.0
		Next
	End Function
	
	Function DeltaTime_Update(d.tDeltaTime)		
		; Capture current time
		d\TimePreviousFrame = d\TimeCurrentFrame
		d\TimeCurrentFrame	= MilliSecs()
	
		; Update intervals
		d\DeltaList#[d\DeltaNode] = Float(d\TimeCurrentFrame-d\TimePreviousFrame)*d\IdealInterval
		d\DeltaNode = (d\DeltaNode+1) Mod 20
		
		; Calculate delta
		d\Delta# = 0.0
		For i=0 To 19
			d\Delta# = d\Delta# + d\DeltaList#[i]
		Next
		d\Delta# 	= d\Delta#*0.05				; d\Delta/20
		d\InvDelta#	= 1.0/d\Delta#
	End Function 
;~IDEal Editor Parameters:
;~C#Blitz3D