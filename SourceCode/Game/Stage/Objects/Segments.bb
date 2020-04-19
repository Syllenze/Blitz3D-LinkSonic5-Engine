;,.=`^'=.,.=`^'=,.,.=`^'=.,.=`^'=,.,.=`^'=.,.=`^'=,.,.=`^'=.,.=`^'=,.,.=`^'=.,.=`^'=,.,.=`^'=.,.=`^'=,.,.=.,.=`;
;==============================================================================================================;
; BlitzSonic Freerunner Engine -- An improved version of the BlitzSonic engine
; BlitzSonic engine created by Damizean, Mark the Echidna, and the BlitzSonic team.
; Version 1.4, August 6, 2012
;
; Project Freerunner and Sonic Freerunner (C) 2010 Freerunner Team
;
;--------------------------------------------------------------------------------------------------------------;
;
; This code is subject to a creative commons license. Please read 
; below for more information about the use and distribution of the 
; source code. If you use this code for your own project, please 
; give credit where needed. Thank you.
;
; -Team Freerunner
;
;==============================================================================================================;
;,.=`^'=.,.=`^'=,.,.=`^'=.,.=`^'=,.,.=`^'=.,.=`^'=,.,.=`^'=.,.=`^'=,.,.=`^'=.,.=`^'=,.,.=`^'=.,.=`^'=,.,.=.,.=`;
;
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
;	Project Title : Sonic Freerunner                                                                           ;
; ============================================================================================================ ;
;	Author : Team Freerunner                                                                                   ;
;	Email :                                                                                                    ;
;	Version: 1.4                                                                                               ;
;	Date: 07/28/2012                                                                                           ;
;                                                                                                              ;
;\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/;
;                                                                                                              ;
;   Changelog:  EightBitDragon                                                                                 ;
;               Saturday, July 28, 2012 - Created file                                                         ;
;                                                                                                              ;
;==============================================================================================================;
;                                                                                                              ;
;   TODO:                                                                                                      ;
;         - Remove pivot collection and replace with arrays                                                    ;
;                                                                                                              ;
;==============================================================================================================;

; /\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/
;   CONSTANTS
; /\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/

	; Part types
	Const PARTTYPE_DEFAULT = 0
	Const PARTTYPE_JR = 1
	Const PARTTYPE_BLOOMINATOR = 2
	Const PARTTYPE_AFTERIMAGE = 3
	
; /\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/
;   STRUCTURES
; /\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/

	Type tSegment
		Field segType
		Field Entity
		Field Pivot
		Field AAMesh.tAAMesh
		Field x#,y#,z#
		Field IValues[3]
		Field FValues#[3]
	End Type
	
; /\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/
;   METHODS
; /\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/

	Function Segments_Update(d.tDeltaTime)
		Local edDistance#
		
		; Loop through players and segment objects
		For p.tPlayer = Each tPlayer
			For s.tSegment = Each tSegment
			
				; Get object distance
				;edDistance# = EntityDistance(p\Objects\Entity, s\Entity)
				
				;If (edDistance# < OBJECT_VIEWDISTANCE_MAX#) Then
					; Show the object
					;ShowEntity(s\Entity)
					
					; Select the segment type and update
					Select s\segType
					
						Case PARTTYPE_AFTERIMAGE
							Update_AfterImage(s, d)
							
						Case PARTTYPE_JR
							Update_Jr_Splash_AAMesh(s, d)
							
					End Select
				;Else
					;HideEntity(s\Entity)
				;End If
			Next
		Next
	End Function
	
