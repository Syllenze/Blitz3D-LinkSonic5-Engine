	; =========================================================================================================
	; Player_Action_Glide (Knuckles)
	; =========================================================================================================
Function Player_Action_Glide(p.tPlayer, d.tDeltaTime)
	
	If (COMMON_GLIDEDESCENTIONRATE# > -1.2) Then
		COMMON_GLIDEDESCENTIONRATE# = COMMON_GLIDEDESCENTIONRATE# - (GlobalTimerFloat#*0.00019)
	Else
		COMMON_GLIDEDESCENTIONRATE# = -1.2
	EndIf
	
	Select Game\Stage\Properties\Hub
		Case 0
	p\Motion\Speed\x# = Sin(p\Animation\Direction#)*2.9
	p\Motion\Speed\z# = -Cos(p\Animation\Direction#)*2.9
Case 1
	p\Motion\Speed\x# = Sin(p\Animation\Direction#)*1.8
	p\Motion\Speed\z# = -Cos(p\Animation\Direction#)*1.8
End Select

	p\Motion\Speed\y# = COMMON_GLIDEDESCENTIONRATE#
	
	If (ChannelPlaying (Channel_Glide) = False) Then Channel_Glide = PlaySound (Sound_Glide)
	
	If (Input\Hold\ActionA <> 1) Then
		p\Action = ACTION_FALL
	EndIf
	
		; Land
	If (p\Motion\Ground = True) Then Land(p,d)
End Function

	; =========================================================================================================
	; Player_Action_Fly (Tails)
	; =========================================================================================================
Function Player_Action_Fly(p.tPlayer, d.tDeltaTime)
	
;	Select Game\Stage\Properties\Hub
;		Case 0
;			p\Motion\Speed\x# = Sin(p\Animation\Direction#)*2.9
;			p\Motion\Speed\z# = -Cos(p\Animation\Direction#)*2.9
;		Case 1
;			p\Motion\Speed\x# = Sin(p\Animation\Direction#)*1.8
;			p\Motion\Speed\z# = -Cos(p\Animation\Direction#)*1.8
;	End Select
	
	COMMON_YTOPSPEED = -2.3
	COMMON_YACCELERATION# = 0.025
	COMMON_XZTOPSPEED#		= 1.5
	; Method 1
;	If (Input\Pressed\ActionA) And (p\Motion\Speed\y# < 0.7) Then
;		p\Motion\Speed\y# = p\Motion\Speed\y# + 0.5
;		If (p\Motion\GroundSpeed# > 0.9) Then
;			p\Motion\Speed\x# = Sin(p\Animation\Direction#)*0.8
;			p\Motion\Speed\z# = -Cos(p\Animation\Direction#)*0.8
;		EndIf
;	EndIf
	
	; Method 2
	If (Input\Hold\ActionA) And (p\Motion\Speed\y# < 0.9) Then
		If (p\Motion\Speed\y# < 0) Then p\Motion\Speed\y# = p\Motion\Speed\y# + (0.25*d\Delta#)
		If (p\Motion\Speed\y# >= 0) Then p\Motion\Speed\y# = p\Motion\Speed\y# + (0.05*d\Delta#)
	;	If (p\Motion\GroundSpeed# > 1.5) Then
		;	p\Motion\Speed\x# = Sin(p\Animation\Direction#)*(0.95)
	;	p\Motion\Speed\z# = -Cos(p\Animation\Direction#)*(0.95)
		
	;	EndIf
	EndIf
	
		; Method 3
;	If (Input\Hold\ActionA) And (p\Motion\Speed\y# < 0.9) Then
;		p\Motion\Speed\y# = 0.5
;		If (p\Motion\GroundSpeed# > 1.5) Then
		
;			COMMON_XZTOPSPEED#		= 1.5
;	EndIf
;	EndIf
;	p\Motion\Speed\x# = Sin(p\Animation\Direction#)*3.3
;	p\Motion\Speed\z# = -Cos(p\Animation\Direction#)*3.3
	
	If (Input\Hold\ActionB) Then p\Motion\Speed\y# = -2
	
	If (ChannelPlaying (Channel_Glide) = False) Then Channel_Glide = PlaySound (Sound_Glide)
	
		; Land
	If (p\Motion\Ground = True) Then Land(p,d)
End Function

	; =========================================================================================================
	; Player_Action_Climb (Knuckles)
	; =========================================================================================================
Function Player_Action_Climb(p.tPlayer, d.tDeltaTime)
	
;	If (EntityPitch#(p\Objects\Mesh < 10)) Then
;		p\Action = ACTION_COMMON
;	EndIf
	
	p\Motion\Speed\x# = 0
	If (p\Motion\Ground = True) Then p\Motion\Speed\y# = 0
	p\Motion\Speed\z# = 0
	
	If p\Motion\Ground = True Then p\Motion\Speed\y# = 0
	
	If (Input\Hold\Up) Then
		p\Motion\Speed\x# = Sin(p\Animation\Direction#)*0.7
		p\Motion\Speed\z# = -Cos(p\Animation\Direction#)*0.7
	EndIf
	If (Input\Hold\Down) Then
		p\Motion\Speed\x# = Sin(p\Animation\Direction#)*0.7
		p\Motion\Speed\z# = -Cos(p\Animation\Direction#)*0.7
	EndIf
	If (Input\Hold\Left) Then
		p\Motion\Speed\x# = Sin(p\Animation\Direction#)*0.7
		p\Motion\Speed\z# = -Cos(p\Animation\Direction#)*0.7
	EndIf
	If (Input\Hold\Right) Then
		p\Motion\Speed\x# = Sin(p\Animation\Direction#)*0.7
		p\Motion\Speed\z# = -Cos(p\Animation\Direction#)*0.7
	EndIf
	
	
	;PointEntity (p\Objects\Mesh, p\Objects\WallCubeDetect)
	
			; Jump when pressed and if on ground
	If (Input\Pressed\ActionA And p\Motion\Ground=True) Then
		
	;	p\Animation\Animation = p\Animation\Animation + 180
		
		
		
		p\Action = ACTION_JUMP
		PlayRandomJumpSound()
		Channel_Jump = PlaySnd(Sound_Jump, p\Objects\Entity, 1)
	;	p\Motion\Speed\x# = p\Motion\Speed\x#*0.99
		p\Motion\Speed\y# = 0.6
	;	p\Motion\Speed\z# = p\Motion\Speed\z#*0.99
		p\Motion\Speed\x# = Sin(p\Animation\Direction#)*1.8
		p\Motion\Speed\z# = -Cos(p\Animation\Direction#)*1.8
		Player_ConvertGroundToAir(p)
		p\Motion\Ground = False
		
		If (p\Motion\GroundSpeed > 2.5) Then
			p\Animation\Animation = 31
		Else
			p\Animation\Animation = 24
		EndIf
		
			; When jumping, we need to set the align vector instantly, or else the 
			; player may reattach to the wall
		p\Motion\Align\x# = 0.0
		p\Motion\Align\y# = 1.0
		p\Motion\Align\z# = 0.0
		
	End If
	
	If (Input\Pressed\ActionB Or p\Motion\Ground = False) Then ;And Game\Gameplay\RingE > 0) Then
		p\Action = ACTION_FALL
	EndIf
	
End Function
;~IDEal Editor Parameters:
;~C#Blitz3D