; Action Controllers
Function Player_SetSpeed(p.tPlayer, d.tDeltaTime)
	;Select Game\Stage\Properties\Hub
	
	If (Game\Stage\Properties\Hub = 1) Or (Input\Hold\Walk = True) Then p\Motion\Walking = 1 : Else p\Motion\Walking = 0
	
	Select p\Motion\Walking
		Case 0 ; Not walking
	Select p\Character
		Case CHARACTER_SONIC
			; Animation = 14
	;		If (p\Animation\Animation = 14) Then
	;		ElseIf (p\BB_InMove = 1) Then
	;			COMMON_XZTOPSPEED#	= 6.2
	;			COMMON_XZACCELERATION#	= 0.030
	;		Else
	;			COMMON_XZACCELERATION#	= 0.037
				
	;		EndIf
			
			If (p\BB_InMove = 0) Then
				
				COMMON_XZTOPSPEED#		= 4.95
				
				If (p\Motion\GroundSpeed# < 1) Then
					COMMON_XZACCELERATION#	= 0.037
				Else
					COMMON_XZACCELERATION#	= 0.033
				EndIf
				
				
				If ((p\Motion\GroundSpeed# > 1) And (p\Motion\Ground = 1)) Then ANTI_SLIDING_FACTOR# = 2.9 : TURNING_SHARPNESS#		= 12
				If ((p\Motion\GroundSpeed# < 1) And (p\Motion\Ground = 1)) Then ANTI_SLIDING_FACTOR# = 2.4 : TURNING_SHARPNESS#		= 2
				
				If ((p\Motion\GroundSpeed# > 1) And (p\Motion\Ground = 0)) Then ANTI_SLIDING_FACTOR# = 2.3 : TURNING_SHARPNESS#		= 3
				If ((p\Motion\GroundSpeed# < 1) And (p\Motion\Ground = 0)) Then ANTI_SLIDING_FACTOR# = 2.6 : TURNING_SHARPNESS#		= 2
				
			EndIf
			
			If (p\BB_InMove = 1) Then
				COMMON_XZTOPSPEED#		= 6.2
				COMMON_XZACCELERATION#	= 0.038
				TURNING_SHARPNESS#		= 20
				ANTI_SLIDING_FACTOR# 	= 9
			EndIf
			
			If (p\Action = ACTION_ROLL) Then
				COMMON_XZACCELERATION#	= 0.02
				Select p\Motion\Ground
						
					Case True : COMMON_XZDECELERATION# = 0.002 : COMMON_XZACCELERATION# = 0.0
					Case False : COMMON_XZDECELERATION# = 0.0 : COMMON_XZACCELERATION# = 0.010
				End Select
			EndIf
			
			
		Case CHARACTER_TAILS
			COMMON_XZACCELERATION#	= 0.037
			COMMON_XZTOPSPEED#		= 5.15
			
		Case CHARACTER_KNUCKLES
			COMMON_XZACCELERATION#	= 0.037
			
			
	End Select
	; Is walking
Case 1
	Select p\Character
		Case CHARACTER_SONIC
			COMMON_XZACCELERATION#	= 0.037
			COMMON_XZTOPSPEED#		= 1.8
			
		Case CHARACTER_TAILS
			COMMON_XZACCELERATION#	= 0.037
			COMMON_XZTOPSPEED#		= 2.2
			
		Case CHARACTER_KNUCKLES
			COMMON_XZACCELERATION#	= 0.037
			COMMON_XZTOPSPEED#		= 2.2
			
	End Select
End Select
			
End Function

Function Player_ActionA_Air(p.tPlayer, d.tDeltaTime)
	Select p\Character
		Case CHARACTER_SONIC
			p\Action = ACTION_DOUBLEJUMP
		;	p\Motion\Speed\x# = Sin(p\Animation\Direction#)*0.9
		;	p\Motion\Speed\z# = -Cos(p\Animation\Direction#)*0.9
			p\Motion\Speed\y# = 0.9
			Channel_Spin = PlaySnd(Sound_Spin, p\Objects\Entity)
		Case CHARACTER_TAILS
			p\Action = ACTION_FLY
			p\Motion\Speed\y# = 0.6
		Case CHARACTER_KNUCKLES
			p\Action = ACTION_GLIDE
		;	COMMON_GLIDEDESCENTIONRATE#	= -0.4
			Channel_GlideStart = PlaySound (Sound_GlideStart)
			
	End Select
	
End Function

Function Player_ActionB_Air(p.tPlayer, d.tDeltaTime)
	Select p\Character
		Case CHARACTER_SONIC
			p\Action = ACTION_JUMPDASH; : RecursiveAnimate(p\Objects\AnimTimer1, 3, 1, 1, 0)
	;		Channel_JumpDash = PlaySnd(Sound_JumpDash, p\Objects\Entity, 1)
			If p\BB_InMove = 0 Then
			PlayRandomDashSound()		
			p\Motion\Speed\x# = Sin(p\Animation\Direction#)*3
			p\Motion\Speed\z# = -Cos(p\Animation\Direction#)*3
			p\Motion\Speed\y# = -0.4
		Else
			p\Motion\Speed\x# = Sin(p\Animation\Direction#)*5.2
			p\Motion\Speed\z# = -Cos(p\Animation\Direction#)*5.2
			p\Motion\Speed\y# = -0.6
		EndIf
		p\DashRunOutTimer = MilliSecs() + 500
		Case CHARACTER_TAILS
		Case CHARACTER_KNUCKLES
			p\Action = ACTION_STOMPPART2
			
	End Select
End Function
	
Function Player_ActionA_Fall(p.tPlayer, d.tDeltaTime)
	Select p\Character
		Case CHARACTER_SONIC
		Case CHARACTER_TAILS
		Case CHARACTER_KNUCKLES
			p\Action = ACTION_GLIDE
		;	COMMON_GLIDEDESCENTIONRATE#	= -0.4
			Channel_GlideStart = PlaySound (Sound_GlideStart)
			
	End Select
	
End Function
	
Function Player_ActionB_Fast(p.tPlayer, d.tDeltaTime)
	Select p\Character
		Case CHARACTER_SONIC
		;	p\Action = ACTION_SLIDE
			p\ActionBTimer = 0
		;	PlaySound(Sound_SpinKick)
		;	PlayRandomKickSound()
		Case CHARACTER_TAILS
			p\Action = ACTION_ROLL
			p\ActionBTimer = 0
		Case CHARACTER_KNUCKLES
			p\Action = ROLL
			p\ActionBTimer = 0
			
			
	End Select
	
End Function
;~IDEal Editor Parameters:
;~C#Blitz3D