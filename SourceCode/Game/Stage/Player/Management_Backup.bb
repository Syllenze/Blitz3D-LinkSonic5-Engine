If (Speed_Length# < 0.1) Then
	p\Animation\Direction# = ATan2(((Acceleration\x#+DeltaCos#*TURNING_SHARPNESS#)/(TURNING_SHARPNESS#+1))*1.0001,-(Acceleration\z#+DeltaSin#*TURNING_SHARPNESS#)/(TURNING_SHARPNESS#+1))
Else
	p\Animation\Direction# = ATan2(((Acceleration\x#+DeltaCos#*(TURNING_SHARPNESS#+1))/(TURNING_SHARPNESS#+2))*1.0001,-(Acceleration\z#+DeltaSin#*(TURNING_SHARPNESS#+1))/(TURNING_SHARPNESS#+2))
End If

			; Depending on the dot product between current direction and new motion direction
DotProduct# = Vector_DotProductNormalized#(Acceleration, Speed)
If (DotProduct# < 0.0) Then
	
				; If there's an opposite change of motion direction, completely 
	If (p\Motion\Ground = True) Then
		Vector_MultiplyByScalar(Acceleration, 1.2)
		
		If (Speed_Length#>2) Then
			p\Flags\Skidding = True
			If (ChannelPlaying(Channel_Skidding)=False) Then Channel_Skidding = PlaySound(Sound_Skidding)
		End If
	End If
	
	Player_SubstractTowardsZero(Speed, 0.06*d\Delta#)
	
Else If (DotProduct# < 0.4) Then
	
				; If there's a harsh change in motion direction, decrease
				; greatly the motion in current direction and increase acceleration
				; on the new.
	If (p\Motion\Ground = True) Then
		SpeedCompensation\x# = (Speed\x#*39.3+DeltaCos#*Speed_Length#)/39.5*0.99
		SpeedCompensation\z# = (Speed\z#*39.3+DeltaSin#*Speed_Length#)/39.5*0.99
		
		Vector_LinearInterpolation(Speed, SpeedCompensation, d\Delta#)
	Else
		Player_SubstractTowardsZero(Speed, 0.02*d\Delta#)
	EndIf
	
	
				;ORIGINAL
			;	If (p\Motion\Ground = True) Then
			;		SpeedCompensation\x# = (Speed\x#*33+DeltaCos#*Speed_Length#)/34*0.96
			;		SpeedCompensation\z# = (Speed\z#*33+DeltaSin#*Speed_Length#)/34*0.96
			;		
			;		Vector_LinearInterpolation(Speed, SpeedCompensation, d\Delta#)
			;	Else
			;		Player_SubstractTowardsZero(Speed, 0.02*d\Delta#)
			;	EndIf
	
	Vector_MultiplyByScalar(Acceleration, 1.2)
	
Else If (DotProduct# < 0.95) Then
	
				; If there's a mild change in direction, slighty decresae
				; the motion in current direction.
	
				; Default 0.98
	
	If (p\Motion\Ground = True) Then
		SpeedCompensation\x# = (Speed\x#*19+DeltaCos#*Speed_Length#)/20
		SpeedCompensation\z# = (Speed\z#*19+DeltaSin#*Speed_Length#)/20
	Else
		SpeedCompensation\x# = (Speed\x#*21+DeltaCos#*Speed_Length#)/22*0.98
		SpeedCompensation\z# = (Speed\z#*21+DeltaSin#*Speed_Length#)/22*0.98					
	EndIf
	
	
		;	ORIGINAL	
		;		If (p\Motion\Ground = True) Then
		;			SpeedCompensation\x# = (Speed\x#*19+DeltaCos#*Speed_Length#)/20
		;			SpeedCompensation\z# = (Speed\z#*19+DeltaSin#*Speed_Length#)/20
		;		Else
		;			SpeedCompensation\x# = (Speed\x#*21+DeltaCos#*Speed_Length#)/22*0.98
		;			SpeedCompensation\z# = (Speed\z#*21+DeltaSin#*Speed_Length#)/22*0.98					
		;		EndIf
	
	Vector_LinearInterpolation(Speed, SpeedCompensation, d\Delta#)
;~IDEal Editor Parameters:
;~C#Blitz3D