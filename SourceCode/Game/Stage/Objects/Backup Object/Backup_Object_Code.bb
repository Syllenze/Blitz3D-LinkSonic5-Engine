Function Object_SpewRing_Update(o.tObject, p.tPlayer, d.tDeltaTime)
	
	o\Timer = o\Timer+(d\TimeCurrentFrame-d\TimePreviousFrame)
	
	RotateEntity o\Entity, 0, Float#(MilliSecs())*0.2, 0
	If (o\IValues[0] <> 0) Then
		RotateEntity o\IValues[0], 0, Float#(MilliSecs())*0.2, 0
		AlignToVector(o\IValues[0], o\FValues[0], o\FValues[1], o\FValues[2], 2)
	End If
	
	For i = 1 To CountCollisions(o\Pivot)
		Select GetEntityType(CollisionEntity(o\Pivot, i))
			Case COLLISION_WORLD_BOX
				o\Speed\y# = -(o\Speed\y#+0.2)
			Case COLLISION_WORLD_POLYGON
				o\Speed\y# = -(o\Speed\y#+0.2)
			Case COLLISION_WORLD_POLYGON_ALIGN
				o\Speed\y# = -(o\Speed\y#+0.2)
		End Select
	Next
	
	If (LinePick(o\Position\x#, o\Position\y#, o\Position\z#, 0, -OMEGA#, 0)<>0) Then
		o\Speed\y# = Max(o\Speed\y#-(GravityMultiplier#*d\Delta), COMMON_YTOPSPEED#)
		MoveEntity(o\Pivot, o\Speed\x#*d\Delta, o\Speed\y#*d\Delta, o\Speed\z*d\Delta)
		
;	Player_SubstractTowardsValue(o\Speed.tVector, d\Delta, -7)
		
		o\Position\x# = EntityX#(o\Pivot)
		o\Position\y# = EntityY#(o\Pivot)
		o\Position\z# = EntityZ#(o\Pivot)
	End If
	
;	TFormVector(p\Motion\Speed\x#, p\Motion\Speed\y#, p\Motion\Speed\z#, Game\Stage\Gravity, p\Objects\Entity)
;	p\Motion\Speed\x# = 0
;	p\Motion\Speed\y# = TFormedY#()
;	p\Motion\Speed\z# = 0
	
		; Player collided with ring
	If (Abs(EntityX(p\Objects\Entity) - o\Position\x) < 3.5) And (Abs(EntityY(p\Objects\Entity) - o\Position\y) < 3.5) And (Abs(EntityZ(p\Objects\Entity) - o\Position\z) < 3.5) Then
		
			; Add to ring counter
		Game\Gameplay\Rings = Game\Gameplay\Rings + 1
	;		Game\Gameplay\Score = Game\Gameplay\Score + 10						
		
			; Bling!			
		Channel_Ring = playsnd(Sound_Ring, o\Entity, 1)
		;ChannelVolume(Channel_Ring,Game\SoundVol)
		
			; Delete the Ring
		FreeEntity o\Entity
		;FreeEntity o\TestEntity
		FreeEntity o\Pivot
		If (o\IValues[0]) Then FreeEntity(o\IValues[0])
		Delete o\Position
		Delete o
		Return
	EndIf
	
	If o\Timer > 8000 Then
			; Delete the Ring
		FreeEntity o\Entity
		;FreeEntity o\TestEntity
		FreeEntity o\Pivot
		If (o\IValues[0]) Then FreeEntity(o\IValues[0])
		Delete o\Position
		Delete o
		Return
	End If
	
End Function
;~IDEal Editor Parameters:
;~C#Blitz3D