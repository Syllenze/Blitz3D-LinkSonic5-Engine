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
;   VARIABLES
; /\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/
	; ---- Sounds ----

Global AntiRepeater[60]

	;SFX
Global Sound_Jump						= Load3DSound("Sounds/SFX/Jump.wav")
	Global Channel_Jump					= 0
	Global Sound_Roll					= LoadSound("Sounds/SFX/Roll.wav")
	Global Channel_Roll					= 0
	Global Sound_Spindash				= Load3DSound("Sounds/SFX/SpinDash.wav")
	Global Channel_Spindash				= 0
	Global Sound_SpindashRelease		= Load3DSound("Sounds/SFX/SpinDashRelease.wav")
	Global Channel_SpindashRelease		= 0
	Global Sound_Skidding				= Load3DSound("Sounds/SFX/Skidding.wav")
	Global Channel_Skidding				= 0
;	Global Sound_Skidding2				= Load3DSound("Sounds/SFX/Skidding2.wav")
;	Global Channel_Skidding2			= 0
	Global Sound_JumpDash				= Load3DSound("Sounds/SFX/JumpDash.wav")	
	Global Channel_JumpDash				= 0
	Global Sound_Landing				= Load3DSound("Sounds/SFX/Landing.wav")	
	Global Channel_Landing				= 0
	Global Sound_SpinKick				= Load3DSound("Sounds/SFX/SpinKick.wav")	
	Global Channel_SpinKick				= 0
	Global Sound_Spin					= Load3DSound("Sounds/SFX/Spin.wav")	
	Global Channel_Spin					= 0
	Global Sound_BBlast					= Load3DSound("Sounds/SFX/BBlast.wav")	
	Global Channel_BBlast				= 0
	Global Sound_BlastWind				= Load3DSound("Sounds/SFX/BlastWind.wav")	
	Global Channel_BlastWind			= 0
	Global Sound_Bounce					= Load3DSound("Sounds/SFX/Bounce.wav")	
	Global Channel_Bounce				= 0
	Global Sound_Loss					= Load3DSound("Sounds/SFX/Loss.wav")	
	Global Channel_Loss					= 0
	Global Sound_Hurt					= Load3DSound("Sounds/SFX/Hurt.wav")	
	Global Channel_Hurt					= 0
	Global Sound_GlideStart				= Load3DSound("Sounds/SFX/GlideStart.wav")	
	Global Channel_GlideStart			= 0
	Global Sound_Glide					= LoadSound("Sounds/SFX/Glide.wav")	
	Global Channel_Glide				= 0
	Global Sound_Underwater				= LoadSound("Sounds/SFX/Underwater.wav")	
	Global Channel_Underwater			= 0
	Global Sound_WallHit				= Load3DSound("Sounds/SFX/WallHit.wav")	
	Global Channel_WallHit				= 0
	
	;VOICE
	Global Stream
	
	Global Sound_JumpVox1
	Global Channel_JumpVox1				= 0
	Global Sound_JumpVox2				
	Global Channel_JumpVox2				= 0
	Global Sound_JumpVox3				
	Global Channel_JumpVox3				= 0	
	Global Sound_DashVox1			
	Global Channel_DashVox1				= 0
	Global Sound_DashVox2				
	Global Channel_DashVox2				= 0
	Global Sound_DashVox3				
	Global Channel_DashVox3				= 0	
	Global Sound_HurtVox1			
	Global Channel_HurtVox1				= 0
	Global Sound_HurtVox2				
	Global Channel_HurtVox2				= 0
	Global Sound_HurtVox3				
	Global Channel_HurtVox3				= 0
	Global Sound_KickVox1				
	Global Channel_KickVox1				= 0
	Global Sound_KickVox2				
	Global Channel_KickVox2				= 0
	Global Sound_KickVox3				
	Global Channel_KickVox3				= 0	
	Global Sound_PickUpVox				
	Global Channel_PickUpVox			= 0	
	Global Sound_ThrowVox				
	Global Channel_ThrowVox				= 0	
	
	Global Sound_IdleVox1				
	Global Channel_IdleVox1				= 0
	Global Sound_IdleVox2				
	Global Channel_IdleVox2				= 0
	Global Sound_IdleVox3				
	Global Channel_IdleVox3				= 0
	Global Sound_IdleVox4				
	Global Channel_IdleVox4				= 0
	Global Sound_IdleVox5				
	Global Channel_IdleVox5				= 0
	Global Sound_IdleVox6				
	Global Channel_IdleVox6				= 0
	
;	Global Sound_JumpVox1				= Load3DSound("Sounds/VOX/JumpVox1.wav")
;	Global Channel_JumpVox1				= 0
;	Global Sound_JumpVox2				= Load3DSound("Sounds/VOX/JumpVox2.wav")
;	Global Channel_JumpVox2				= 0
;	Global Sound_JumpVox3				= Load3DSound("Sounds/VOX/JumpVox3.wav")
;	Global Channel_JumpVox3				= 0	
;	Global Sound_DashVox1				= Load3DSound("Sounds/VOX/DashVox1.wav")
;	Global Channel_DashVox1				= 0
;	Global Sound_DashVox2				= Load3DSound("Sounds/VOX/DashVox2.wav")
;	Global Channel_DashVox2				= 0
;	Global Sound_DashVox3				= Load3DSound("Sounds/VOX/DashVox3.wav")
;	Global Channel_DashVox3				= 0	
;	Global Sound_HurtVox1				= Load3DSound("Sounds/VOX/HurtVox1.wav")
;	Global Channel_HurtVox1				= 0
;	Global Sound_HurtVox2				= Load3DSound("Sounds/VOX/HurtVox2.wav")
;	Global Channel_HurtVox2				= 0
;	Global Sound_HurtVox3				= Load3DSound("Sounds/VOX/HurtVox3.wav")
;	Global Channel_HurtVox3				= 0
;	Global Sound_KickVox1				= Load3DSound("Sounds/VOX/KickVox1.wav")
;	Global Channel_KickVox1				= 0
;	Global Sound_KickVox2				= Load3DSound("Sounds/VOX/KickVox2.wav")
;	Global Channel_KickVox2				= 0
;	Global Sound_KickVox3				= Load3DSound("Sounds/VOX/KickVox3.wav")
;	Global Channel_KickVox3				= 0	
	
	;MENU
	Global Sound_SelectLevel			= LoadSound("Sounds/Menu/SelectLevel.wav")	
	Global Channel_SelectLevel			= 0
	Global Sound_BackToMenu				= LoadSound("Sounds/Menu/BackToMenu.wav")	
	Global Channel_BackToMenu			= 0
	Global Sound_Menu					= LoadSound("Music/Menu.mp3")	
	Global Channel_Menu					= 0
	Global Sound_MenuEnter				= LoadSound("Sounds/Menu/MenuEnter.wav")	
	Global Channel_MenuEnter			= 0
	Global Sound_MenuExit				= LoadSound("Sounds/Menu/MenuExit.wav")	
	Global Channel_MenuExit				= 0
	Global Sound_MenuMessage			= LoadSound("Sounds/Menu/MenuMessage.wav")	
	Global Channel_MenuMessage			= 0
	
	;FOOTSTEP
	Global Sound_Step1					= Load3DSound("Sounds/Footstep/Step1.wav")	
	Global Channel_Step1				= 0
	Global Sound_Step2					= Load3DSound("Sounds/Footstep/Step2.wav")	
	Global Channel_Step2				= 0
	Global Sound_Step3					= Load3DSound("Sounds/Footstep/Step3.wav")	
	Global Channel_Step3				= 0
	Global Sound_Step4					= Load3DSound("Sounds/Footstep/Step4.wav")	
	Global Channel_Step4				= 0
	Global Sound_Step5					= Load3DSound("Sounds/Footstep/Step5.wav")	
	Global Channel_Step5				= 0
	Global Sound_Step6					= Load3DSound("Sounds/Footstep/Step6.wav")	
	Global Channel_Step6				= 0
	Global Sound_Step7					= Load3DSound("Sounds/Footstep/Step7.wav")	
	Global Channel_Step7				= 0
	
	Global Sound_AirStep				= Load3DSound("Sounds/Footstep/AirStep.wav")	
	Global Channel_AirStep				= 0
	
	;ONLINE
	Global Sound_Message				= LoadSound("Sounds/Online/Message.wav")	
	Global Channel_Message				= 0
	Global Sound_Join					= LoadSound("Sounds/Online/Join.wav")	
	Global Channel_Join					= 0
	
	;OBJECTS
	Global Sound_TV						= Load3DSound("Sounds/Objects/TV.wav")	
	Global Channel_TV					= 0
	Global Sound_End					= LoadSound("Sounds/System/StageClear.mp3")	
	Global Channel_End					= 0
	Global Sound_Bomber					= Load3DSound("Sounds/Objects/Bomber.wav")	
	Global Channel_Bomber				= 0
	Global Sound_Damage1				= Load3DSound("Sounds/Objects/Damage1.wav")	
	Global Channel_Damage1				= 0
	Global Sound_Damage2				= Load3DSound("Sounds/Objects/Damage2.wav")	
	Global Channel_Damage2				= 0
	Global Sound_Damage3				= Load3DSound("Sounds/Objects/Damage3.wav")	
	Global Channel_Damage3				= 0
	Global Sound_Hoop					= Load3DSound("Sounds/Objects/Hoop.wav")	
	Global Channel_Hoop					= 0
	Global Sound_THoop					= Load3DSound("Sounds/Objects/THoop.wav")	
	Global Channel_THoop				= 0
	Global Sound_Check					= Load3DSound("Sounds/Objects/CheckPoint.wav")	
	Global Channel_Check				= 0
	Global Sound_DashPad				= Load3DSound("Sounds/Objects/DashPad.wav")	
	Global Channel_DashPad				= 0
	Global Sound_DashRamp				= Load3DSound("Sounds/Objects/DashRamp.wav")	
	Global Channel_DashRamp				= 0
	Global Sound_Bumper					= Load3DSound("Sounds/Objects/Bumper.wav")	
	Global Channel_Bumper				= 0
	Global Sound_Target					= Load3DSound("Sounds/Objects/Target.wav")	
	Global Channel_Target				= 0
	Global Sound_Explode				= Load3DSound("Sounds/Objects/Explode.wav")	
	Global Channel_Explode				= 0
	Global Sound_Goal					= Load3DSound("Sounds/Objects/Goal.wav")	
	Global Channel_Goal					= 0
	Global Sound_GoalIdle				= Load3DSound("Sounds/Objects/GoalIdle.wav")	
	Global Channel_GoalIdle				= 0
	Global Sound_Balloon				= Load3DSound("Sounds/Objects/Balloon.wav")	
	Global Channel_Balloon				= 0
	Global Sound_Ring					= Load3DSound("Sounds/Objects/Ring.wav")
	Global Channel_Ring					= 0	
	Global Sound_Spring					= Load3DSound("Sounds/Objects/Spring.wav")	
	Global Channel_Spring				= 0		
	Global Sound_Explosion				= Load3DSound("Sounds/Objects/TV.wav")	
	Global Channel_Explosion			= 0
	Global Sound_RingBounce1			= Load3DSound("Sounds/Objects/RingBounce1.wav")	
	Global Channel_RingBounce1			= 0
	Global Sound_RingBounce2			= Load3DSound("Sounds/Objects/RingBounce2.wav")	
	Global Channel_RingBounce2			= 0
	Global Sound_RingBounce3			= Load3DSound("Sounds/Objects/RingBounce3.wav")	
	Global Channel_RingBounce3			= 0
	Global Sound_Hint					= Load3DSound("Sounds/Objects/Hint.wav")	
	Global Channel_Hint					= 0
	Global Sound_PCOpen					= Load3DSound("Sounds/Objects/TerminalOpen.wav")	
	Global Channel_PCOpen				= 0
	Global Sound_PCClose				= Load3DSound("Sounds/Objects/TerminalClose.wav")	
	Global Channel_PCClose				= 0
	Global Sound_Shoot					= Load3DSound("Sounds/Objects/Shoot.wav")	
	Global Channel_Shoot				= 0
	Global Sound_Spinner				= Load3DSound("Sounds/Objects/Spinner.wav")	
	Global Channel_Spinner				= 0
	
	;MISC
	Global Sound_Error					= LoadSound("Sounds/Misc/Error.wav")	
	Global Channel_Error				= 0
	Global Sound_Rain					= LoadSound("Sounds/Misc/Rain.wav")	
	Global Channel_Rain					= 0
	
	Global Sound_Totaka					= LoadSound("Totaka.wav")	
	Global Channel_Totaka				= 0
	
	Global Channel_Sounds[128]
	
	Global Channel_NPC					= 0
	Global Channel_Ambient				= 0
	
	; -----------------------------
	; Random Sound Effect Functions
	; -----------------------------
	
Function PlayRandomJumpSound()
	For p.tPlayer = Each tPlayer
		Select (Rand(1,5))
				
			Case 1 : Return playsnd(Sound_JumpVox1, p\Objects\Entity, 1); : Stream = BASS_StreamCreateFile(0,"Sounds/VOX/JumpVox1.wav",0,0,BASS_SAMPLE_LOOP)
			Case 2 : Return playsnd(Sound_JumpVox2, p\Objects\Entity, 1); : Stream = BASS_StreamCreateFile(0,"Sounds/VOX/JumpVox2.wav",0,0,BASS_SAMPLE_LOOP)
			Case 3 : Return playsnd(Sound_JumpVox3, p\Objects\Entity, 1); : Stream = BASS_StreamCreateFile(0,"Sounds/VOX/JumpVox3.wav",0,0,BASS_SAMPLE_LOOP)
				
		End Select
	Next
End Function

Function PlayRandomDashSound()
	For p.tPlayer = Each tPlayer
		Select (Rand(1,5))
				
			Case 1 : Return Channel_DashVox1 = playsnd(Sound_DashVox1, p\Objects\Entity, 1); : Stream = BASS_StreamCreateFile(0,"Sounds/VOX/DashVox1.wav",0,0,BASS_SAMPLE_LOOP)
			Case 2 : Return Channel_DashVox2 = playsnd(Sound_DashVox2, p\Objects\Entity, 1); : Stream = BASS_StreamCreateFile(0,"Sounds/VOX/DashVox2.wav",0,0,BASS_SAMPLE_LOOP)
			Case 3 : Return Channel_DashVox3 = playsnd(Sound_DashVox3, p\Objects\Entity, 1); : Stream = BASS_StreamCreateFile(0,"Sounds/VOX/DashVox3.wav",0,0,BASS_SAMPLE_LOOP)
				
		End Select
	Next
End Function

Function PlayRandomHurtSound()
	For p.tPlayer = Each tPlayer
		Select (Rand(1,3))
				
			Case 1 : Return Channel_HurtVox1 = playsnd(Sound_HurtVox1, p\Objects\Entity, 1); : Stream = BASS_StreamCreateFile(0,"Sounds/VOX/HurtVox1.wav",0,0,BASS_SAMPLE_LOOP)
			Case 2 : Return Channel_HurtVox2 = playsnd(Sound_HurtVox2, p\Objects\Entity, 1); : Stream = BASS_StreamCreateFile(0,"Sounds/VOX/HurtVox1.wav",0,0,BASS_SAMPLE_LOOP)
			Case 3 : Return Channel_HurtVox3 = playsnd(Sound_HurtVox3, p\Objects\Entity, 1); : Stream = BASS_StreamCreateFile(0,"Sounds/VOX/HurtVox1.wav",0,0,BASS_SAMPLE_LOOP)
				
		End Select
	Next
End Function

Function PlayRandomDamageSound()
	For p.tPlayer = Each tPlayer
		Select (Rand(1,3))
				
			Case 1 : Return Channel_Damage1 = playsnd(Sound_Damage1, p\Objects\Entity, 1)
			Case 2 : Return Channel_Damage2 = playsnd(Sound_Damage2, p\Objects\Entity, 1)
			Case 3 : Return Channel_Damage3 = playsnd(Sound_Damage3, p\Objects\Entity, 1)
				
		End Select
	Next
End Function

Function PlayRandomIdleSound()
	If Online = 0 Then
	For p.tPlayer = Each tPlayer
		Select (Rand(1,12))
				
			Case 1 : Return Channel_IdleVox1 = playsnd(Sound_IdleVox1, p\Objects\Entity, 1)
			Case 2 : Return Channel_IdleVox2 = playsnd(Sound_IdleVox2, p\Objects\Entity, 1)
			Case 3 : Return Channel_IdleVox3 = playsnd(Sound_IdleVox3, p\Objects\Entity, 1)
			Case 4 : Return Channel_IdleVox4 = playsnd(Sound_IdleVox4, p\Objects\Entity, 1)
			Case 5 : Return Channel_IdleVox5 = playsnd(Sound_IdleVox5, p\Objects\Entity, 1)
			Case 6 : Return Channel_IdleVox6 = playsnd(Sound_IdleVox6, p\Objects\Entity, 1)
			Case 7
			Case 8
			Case 9
			Case 10
			Case 11
			Case 12
				
		End Select
	Next
	EndIf
End Function

Function PlayRandomStepSound()
	For p.tPlayer = Each tPlayer
		If (p\Motion\Ground = True) And FootStepEnabled=1 Then
			
			Select (Rand(1,6))
				Case 1 
					If (ChannelPlaying(Channel_Step1))=False Then Return Channel_Step1 = playsnd(Sound_Step1, p\Objects\Entity, 1)
				Case 2
					If (ChannelPlaying(Channel_Step1))=False Then Return Channel_Step1 = playsnd(Sound_Step2, p\Objects\Entity, 1)
				Case 3
					If (ChannelPlaying(Channel_Step1))=False Then Return Channel_Step1 = playsnd(Sound_Step3, p\Objects\Entity, 1)
				Case 4
					If (ChannelPlaying(Channel_Step1))=False Then Return Channel_Step1 = playsnd(Sound_Step4, p\Objects\Entity, 1)
				Case 5
					If (ChannelPlaying(Channel_Step1))=False Then Return Channel_Step1 = playsnd(Sound_Step5, p\Objects\Entity, 1)
				Case 6
					If (ChannelPlaying(Channel_Step1))=False Then Return Channel_Step1 = playsnd(Sound_Step6, p\Objects\Entity, 1)
			End Select
			
		EndIf
	Next
	
	For p.tPlayer = Each tPlayer
		If (p\Motion\Ground = False) And FootStepEnabled=1 Then
			
			Select (Rand(1,6))
					
				Case 1 
					If (ChannelPlaying(Channel_Step1))=False Then
						Return Channel_Step1 = playsnd(Sound_AirStep, p\Objects\Entity, 1)
					EndIf
				Case 2 
					If (ChannelPlaying(Channel_Step1))=False Then
						Return Channel_Step1 = playsnd(Sound_AirStep, p\Objects\Entity, 1)
					EndIf
				Case 3 
					If (ChannelPlaying(Channel_Step1))=False Then
						Return Channel_Step1 = playsnd(Sound_AirStep, p\Objects\Entity, 1)
					EndIf
				Case 4 
					If (ChannelPlaying(Channel_Step1))=False Then
						Return Channel_Step1 = playsnd(Sound_AirStep, p\Objects\Entity, 1)
					EndIf
				Case 5 
					If (ChannelPlaying(Channel_Step1))=False Then
						Return Channel_Step1 = playsnd(Sound_AirStep, p\Objects\Entity, 1)
					EndIf
				Case 6
					If (ChannelPlaying(Channel_Step1))=False Then
						Return Channel_Step1 = playsnd(Sound_AirStep, p\Objects\Entity, 1)
					EndIf
			End Select
			
		EndIf
	Next
End Function

Function PlayRandomKickSound()
	For p.tPlayer = Each tPlayer
		Select (Rand(1,5))
			Case 1 : Return Channel_KickVox1 = playsnd(Sound_KickVox1, p\Objects\Entity, 1)
			Case 2 : Return Channel_KickVox2 = playsnd(Sound_KickVox2, p\Objects\Entity, 1)
			Case 3 : Return Channel_KickVox3 = playsnd(Sound_KickVox3, p\Objects\Entity, 1)
		End Select
	Next
End Function

Function PlayRandomSkiddingSound() ;Broken
	For p.tPlayer = Each tPlayer
		If ((ChannelPlaying(Channel_Skidding1=False)) And (ChannelPlaying(Channel_Skidding2=False))) Then
			Select (Rand(1,2))
				Case 1 : Return Channel_Skidding1 = playsnd(Sound_Skidding1, p\Objects\Entity, 1)
				Case 2 : Return Channel_Skidding2 = playsnd(Sound_Skidding2, p\Objects\Entity, 1)
			End Select
		EndIf
	Next
End Function

Function PlayRandomRingBounceSound()
	For p.tPlayer = Each tPlayer
			Select (Rand(1,3))
				Case 1 : StopChannel(Channel_RingBounce1) : Channel_RingBounce1 = PlaySound(Sound_RingBounce1)
				Case 2 : StopChannel(Channel_RingBounce1) : Channel_RingBounce1 = PlaySound(Sound_RingBounce2)
				Case 3 : StopChannel(Channel_RingBounce1) : Channel_RingBounce1 = PlaySound(Sound_RingBounce3)
			End Select
	Next
End Function
	
	
	; XML Load
;	Global Sound_XMLSound[200]
;	Global Channel_XMLSound[200]		= 0
	
	;---------------
	;Sound Functions
	;---------------
	
	Type sample
		Field snd
	End Type
	
Function playsnd(snd,src_ent=0,vol#=1)
	nosounds = True
	If snd = 0 Then 
;   DebugLog "no sound to play"
		Return
	End If
	For as.sample = Each sample
		If Not ChannelPlaying(as\snd) Then 
			nosounds = False
			Exit
		End If
	Next
	If nosounds Then as.sample = New sample
	SoundVolume snd,vol
	
	If src_ent = 0 Then
		as\snd=PlaySound(snd)
	Else 
		as\snd=EmitSound(snd,src_ent)
	End If
	
;	debuglog snd +" = " +		as\snd
	Return as\snd
	
End Function
;~IDEal Editor Parameters:
;~C#Blitz3D