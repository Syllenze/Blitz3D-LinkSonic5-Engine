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
;               16/01/2008 - Code reorganization.                                                              ;
;                          - Rewrote some chunks of code to adapt to changes on the Maths library. The code    ;
;                            is now overall better readable.                                                   ;
;                                                                                                              ;
;==============================================================================================================;
;                                                                                                              ;
;   TODO:   - Add support for non-aligneable meshes. This will increase overall movement's stabilities on      ;
;             certain meshes.                                                                                  ;
;           - Get a Life                                                                                       ;                                                                                                      ;
;                                                                                                              ;
;==============================================================================================================;

; /\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/
; 	METHODS
; /\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/
Global PlayerRotation# = 0.0
	; ---- Motion routines ----
	; =========================================================================================================
	; Player_Motion
	; =========================================================================================================
Function Player_Motion(p.tPlayer, d.tDeltaTime)
	
	; Declarate acceleration and speed vectors and setup.
	Acceleration.tVector 		= Vector(Cos#(MotionDirection#)*MotionPressure#, 0, Sin#(MotionDirection#)*MotionPressure#)
	Speed.tVector 		 		= Vector(p\Motion\Speed\x#, 0, p\Motion\Speed\z#)
	SpeedCompensation.tVector	= Vector(0, 0, 0)
	Speed_Length#				= Vector_Length#(Speed)
	
	
	
		; Test out collisions with scenery since last update. While doing this, calculate if character's on
		; ground, and if so, set the alignment.
	Ground = Player_TestCollisions(p, d)
	
	If (p\Action = ACTION_CLIMB) Then
		MOTION_GROUND#			= -1
		MOTION_CEILING#			= -1
		MOTION_CEILING_STOP#	= -1
		MOTION_WALL_UP#			= -1
		MOTION_WALL_DOWN#		= -1
		MOTION_WALL_DIRECTION#	= -1
		COMMON_XZTOPSPEED#	= 0.8
	Else
		MOTION_GROUND#			= 0.65
		MOTION_CEILING#			= -0.65
		MOTION_CEILING_STOP#		= -0.79
		MOTION_WALL_UP#			= -0.7
		MOTION_WALL_DOWN#		= 0.2
		MOTION_WALL_DIRECTION#	= 0.3
	EndIf

		; Once we know if the character's on ground, check for the ground flag and change motion speed in
		; consecuence.
		Select Ground
			Case True
				Player_Align(p)
				
				If p\Action = ACTION_ROLL Then
					Player_ConvertGroundToRoll(p)
				End If
				
				If (p\Motion\Ground = False) Then
					; If character just landed, transpose air speed to ground	
					Player_ConvertAirToGround(p)
					
					Jump_Trail_Reset(p)
					p\Motion\Ground = True
					
				;	If (p\FallTimer < MilliSecs()) Then p\Action = ACTION_FALL
						
						; Land
				;	If (p\Motion\Ground = True) Then Land(p,d)
					
						
					; Change alignment
					p\Animation\Align\x# = p\Motion\Align\x#
					p\Animation\Align\y# = p\Motion\Align\y#
					p\Animation\Align\z# = p\Motion\Align\z#
				End If
			Case False
				; If character just landed, transpose air speed to ground
				If (p\Motion\Ground = True) Then
					Player_ConvertGroundToAir(p)
					p\Motion\Ground = False
				End If
				
				If p\Action = ACTION_ROLL Or p\Grinding Then
					Player_ConvertGroundToRoll(p)
				End If
				
				Player_Align(p)
		End Select

		; Smoothly change animation alignment to the one of the player
		
		If (p\Motion\Ground = True) Then Vector_LinearInterpolation(p\Animation\Align, p\Motion\Align, (0.08+Vector_Length#(p\Motion\Speed)*0.05)*d\Delta#)
		If (p\Motion\Ground = False) Then Vector_LinearInterpolation(p\Animation\Align, p\Motion\Align, (0.01+Vector_Length#(p\Motion\Speed)*0.015)*d\Delta#)
		Vector_Normalize(p\Animation\Align)

		; Change direction of the mesh
		PositionEntity(p\Objects\Mesh, EntityX(p\Objects\Entity), EntityY(p\Objects\Entity), EntityZ(p\Objects\Entity))
		RotateEntity(p\Objects\Mesh, 0, p\Animation\Direction#, 0)
		
	;	If (p\Action = ACTION_DESTSPRING) Then
		AlignToVector(p\Objects\Mesh, p\Animation\Align\x#, p\Animation\Align\y#, p\Animation\Align\z#, 2)
	;	Else
	;		AlignToVector(p\Objects\Mesh, p\rx#, p\ry#, p\rz#, 2)
	;	EndIf
		
		
;		RotateEntity(p\Objects\Mesh_SpeedBubble, 0, 0, 0)
;		PositionEntity p\Objects\Mesh_SpeedBubble, EntityX(p\Objects\Mesh), EntityY(p\Objects\Mesh), EntityZ(p\Objects\Mesh)
;		RotateEntity(p\Objects\Mesh_SpeedBubble, 0, p\Animation\Direction#, 0)
;		AlignToVector(p\Objects\Mesh_SpeedBubble, p\Motion\Align\x#, p\Motion\Align\y#, p\Motion\Align\z#, 2)

		; Update shadow
		If (LinePick(EntityX(p\Objects\Entity), EntityY(p\Objects\Entity), EntityZ(p\Objects\Entity), 0, -OMEGA#, 0)<>0) Then
			PositionEntity(p\Objects\Shadow, PickedX#(), PickedY#()+0.1, PickedZ#(), True)
			AlignToVector(p\Objects\Shadow, PickedNX#(), PickedNY#(), PickedNZ#(), 2)
			ScaleEntity(p\Objects\Shadow, 1-Min#((EntityY#(p\Objects\Entity)-PickedY#())/120, 1), 1-Min#((EntityY#(p\Objects\Entity)-PickedY#())/120, 1), 1-Min#((EntityY#(p\Objects\Entity)-PickedY#())/120, 1))
			EntityAlpha(p\Objects\Shadow, 1-Min#((EntityY#(p\Objects\Entity)-PickedY#())/120, 1))
			
			ShowEntity(p\Objects\Shadow)
		Else
			HideEntity(p\Objects\Shadow)
		End If
		
		; Update global PlayerRotation for particles
		PlayerRotation# = p\Animation\Direction#
		
		; Now, just move over the character to the new position, based on its speed.
		If (p\Motion\Ground=True) Then
			MoveEntity(p\Objects\Entity, p\Motion\Speed\x#*d\Delta, p\Motion\Speed\y#*d\Delta-(0.015+(Vector_Length#(p\Motion\Speed)*0.33*d\Delta)), p\Motion\Speed\z#*d\Delta)
		Else	
			MoveEntity(p\Objects\Entity, p\Motion\Speed\x#*d\Delta, p\Motion\Speed\y#*d\Delta, p\Motion\Speed\z*d\Delta)
		EndIf
	End Function


	; =========================================================================================================
	; Player_TestCollisions
	; =========================================================================================================
	Function Player_TestCollisions(p.tPlayer, d.tDeltaTime)
		; Define values for the dot product to be considered up and down collisions
		CeilingTest# = -MOTION_CEILING#
		GroundTest#  = MOTION_GROUND#
		FrontTest#	 = MOTION_WALL_DIRECTION#
		FrontFactor# = 0

		Align		 = False
		ShouldAlign	 = False
		Result		 = False
		
		; Create normal vectors for temporaly storing the alignment
		CollisionNormal.tVector = Vector(0, 0, 0)
		GroundNormal.tVector	= Vector(0, 0, 0)
		CeilingNormal.tVector	= Vector(0, 0, 0)
		SpeedNormal.tVector		= Vector_Copy(p\Motion\Speed) : Vector_Normalize(SpeedNormal)
		
		; Iterate through each collision and register all the collision data
		; that may have ocurred within the player sphere.
		For i = 1 To CountCollisions(p\Objects\Entity)
			; Clear current collision normal
			Vector_Set(CollisionNormal, 0, 0, 0)
			ShouldAlign = False
			
			; Add vertex normals to our normal vector
			Select GetEntityType(CollisionEntity(p\Objects\Entity, i))
				Case COLLISION_WORLD_POLYGON_ALIGN
					; Setup
					ShouldAlign = True
					
					Col = GetEntityType(CollisionEntity(p\Objects\Entity, i))
					
					; Acquire collided surface & triangle
					Surface 	= CollisionSurface(p\Objects\Entity, i)
					Triangle 	= CollisionTriangle(p\Objects\Entity, i)
					
					For j = 0 To 2
						Vertex		  	   = TriangleVertex(Surface, Triangle, j)
						CollisionNormal\x# = CollisionNormal\x#+VertexNX#(Surface, Vertex)
						CollisionNormal\y# = CollisionNormal\y#+VertexNY#(Surface, Vertex)
						CollisionNormal\z# = CollisionNormal\z#+VertexNZ#(Surface, Vertex)
					Next
					Vector_Normalize(CollisionNormal)
	
					; Calculate dot product
					DotProduct# = Vector_DotProduct#(CollisionNormal, p\Motion\Align)

				Case COLLISION_WORLD_POLYGON
					; Setup
					ShouldAlign = False
					
					Col = GetEntityType(CollisionEntity(p\Objects\Entity, i))
					
					; Acquire collided surface & triangle
					Surface 	= CollisionSurface(p\Objects\Entity, i)
					Triangle 	= CollisionTriangle(p\Objects\Entity, i)
					
					For j = 0 To 2
						Vertex		  	   = TriangleVertex(Surface, Triangle, j)
						CollisionNormal\x# = CollisionNormal\x#+VertexNX#(Surface, Vertex)
						CollisionNormal\y# = CollisionNormal\y#+VertexNY#(Surface, Vertex)
						CollisionNormal\z# = CollisionNormal\z#+VertexNZ#(Surface, Vertex)
					Next

					Vector_Normalize(CollisionNormal)
					
	
					; Calculate dot product
					DotProduct# = Vector_DotProduct#(CollisionNormal, Game\Stage\GravityAlignment)
					
				Case COLLISION_WORLD_DIRCORRECT
					; Setup
					ShouldAlign = True
					
					Col = GetEntityType(CollisionEntity(p\Objects\Entity, i))
					
					; Acquire collided surface & triangle
					Surface 	= CollisionSurface(p\Objects\Entity, i)
					Triangle 	= CollisionTriangle(p\Objects\Entity, i)
					
					For j = 0 To 2
						Vertex		  	   = TriangleVertex(Surface, Triangle, j)
						CollisionNormal\x# = CollisionNormal\x#+VertexNX#(Surface, Vertex)
						CollisionNormal\y# = CollisionNormal\y#+VertexNY#(Surface, Vertex)
						CollisionNormal\z# = CollisionNormal\z#+VertexNZ#(Surface, Vertex)
					Next
					
					
					
					Vector_Normalize(CollisionNormal)
					
				;	If p\Motion\GroundSpeed > 0.8 Then
						
						If (EntityCollided(p\Objects\R_GrindAffector, COLLISION_WORLD_RAIL)) Then
							p\Animation\Direction# = p\Animation\Direction# + (1.7*d\Delta)
						EndIf
						
						If (EntityCollided(p\Objects\L_GrindAffector, COLLISION_WORLD_RAIL)) Then
							p\Animation\Direction# = p\Animation\Direction# - (1.7*d\Delta)
						EndIf
						
						RotateEntity(p\Objects\Mesh, 0, p\Animation\Direction#, 0)
						
				;	EndIf
					
					
					; Calculate dot product
					DotProduct# = Vector_DotProduct#(CollisionNormal, Game\Stage\GravityAlignment)

				Case COLLISION_WORLD_BOX
					; Setup
					ShouldAlign = False
					
					CollisionNormal\x# = CollisionNX#(p\Objects\Entity, i)
					CollisionNormal\y# = CollisionNY#(p\Objects\Entity, i)
					CollisionNormal\z# = CollisionNZ#(p\Objects\Entity, i)

					; Calculate dot product
					DotProduct# = Vector_DotProduct#(CollisionNormal, p\Motion\Align)
					
				Case COLLISION_WORLD_HURT
					; Setup
					ShouldAlign = False
					
					Col = GetEntityType(CollisionEntity(p\Objects\Entity, i))
					
					; Acquire collided surface & triangle
					Surface 	= CollisionSurface(p\Objects\Entity, i)
					Triangle 	= CollisionTriangle(p\Objects\Entity, i)
					
					For j = 0 To 2
						Vertex		  	   = TriangleVertex(Surface, Triangle, j)
						CollisionNormal\x# = CollisionNormal\x#+VertexNX#(Surface, Vertex)
						CollisionNormal\y# = CollisionNormal\y#+VertexNY#(Surface, Vertex)
						CollisionNormal\z# = CollisionNormal\z#+VertexNZ#(Surface, Vertex)
					Next
					
					Vector_Normalize(CollisionNormal)
					
					; Calculate dot product
					DotProduct# = Vector_DotProduct#(CollisionNormal, Game\Stage\GravityAlignment)
					
					ObjectHurt (p, 1)
					
				Case COLLISION_WORLD_DIE
					; Setup
					ShouldAlign = False
					
					Col = GetEntityType(CollisionEntity(p\Objects\Entity, i))
					
					; Acquire collided surface & triangle
					Surface 	= CollisionSurface(p\Objects\Entity, i)
					Triangle 	= CollisionTriangle(p\Objects\Entity, i)
					
					For j = 0 To 2
						Vertex		  	   = TriangleVertex(Surface, Triangle, j)
						CollisionNormal\x# = CollisionNormal\x#+VertexNX#(Surface, Vertex)
						CollisionNormal\y# = CollisionNormal\y#+VertexNY#(Surface, Vertex)
						CollisionNormal\z# = CollisionNormal\z#+VertexNZ#(Surface, Vertex)
					Next
					
					Vector_Normalize(CollisionNormal)
					
					; Calculate dot product
					DotProduct# = Vector_DotProduct#(CollisionNormal, Game\Stage\GravityAlignment)
					
					ObjectHurt (p, 3)
					
				Case COLLISION_WORLD_FALL
					; Setup
					ShouldAlign = False
					
					
					; Acquire collided surface & triangle
					Surface 	= CollisionSurface(p\Objects\Entity, i)
					Triangle 	= CollisionTriangle(p\Objects\Entity, i)
					
					For j = 0 To 2
						Vertex		  	   = TriangleVertex(Surface, Triangle, j)
						CollisionNormal\x# = CollisionNormal\x#+VertexNX#(Surface, Vertex)
						CollisionNormal\y# = CollisionNormal\y#+VertexNY#(Surface, Vertex)
						CollisionNormal\z# = CollisionNormal\z#+VertexNZ#(Surface, Vertex)
					Next
					
					Vector_Normalize(CollisionNormal)
					
					; Calculate dot product
					DotProduct# = Vector_DotProduct#(CollisionNormal, Game\Stage\GravityAlignment)
					
					EntityType p\Objects\Entity, 0
					p\Motion\Speed\y# = -1.5
					ObjectHurt (p, 5)
					StopChannel(Channel_Landing)
					
				Case COLLISION_WORLD_RAIL
					; Setup
					ShouldAlign = True
					
					Col = GetEntityType(CollisionEntity(p\Objects\Entity, i))
					
					; Acquire collided surface & triangle
					Surface 	= CollisionSurface(p\Objects\Entity, i)
					Triangle 	= CollisionTriangle(p\Objects\Entity, i)
					
					For j = 0 To 2
						Vertex		  	   = TriangleVertex(Surface, Triangle, j)
						CollisionNormal\x# = CollisionNormal\x#+VertexNX#(Surface, Vertex)
						CollisionNormal\y# = CollisionNormal\y#+VertexNY#(Surface, Vertex)
						CollisionNormal\z# = CollisionNormal\z#+VertexNZ#(Surface, Vertex)
					Next
					
					Vector_Normalize(CollisionNormal)
					
			;		If p\Motion\GroundSpeed > 0.8 Then
					
					If (EntityCollided(p\Objects\R_GrindAffector, COLLISION_WORLD_RAIL)) Then
						p\Animation\Direction# = p\Animation\Direction# + (0.7*d\Delta)
					EndIf
					
					If (EntityCollided(p\Objects\L_GrindAffector, COLLISION_WORLD_RAIL)) Then
						p\Animation\Direction# = p\Animation\Direction# - (0.7*d\Delta)
					EndIf
					
			;	EndIf
					
					RotateEntity(p\Objects\Mesh, 0, p\Animation\Direction#, 0)
					StopChannel(Channel_Boost)
					
					; Calculate dot product
					DotProduct# = Vector_DotProduct#(CollisionNormal, Game\Stage\GravityAlignment)
				;	p\Action = ACTION_GRIND
					
					If (Input\Hold\ActionB = True) Then
						p\Motion\Speed\x# = Sin(p\Animation\Direction#)*4.0
						p\Motion\Speed\z# = -Cos(p\Animation\Direction#)*4.0
				Else
					p\Motion\Speed\x# = Sin(p\Animation\Direction#)*2.6
					p\Motion\Speed\z# = -Cos(p\Animation\Direction#)*2.6
				EndIf
					
				;	If (p\Motion\GroundSpeed < 3 And p\Motion\Ground=True) Then
				;		p\Motion\Speed\x# = p\Motion\Speed\x#*1.04
				;		p\Motion\Speed\z# = p\Motion\Speed\z#*1.04
				;	EndIf
					
				;	If (p\Motion\GroundSpeed < 1 And p\Motion\Ground=True)
				;		p\Motion\Speed\x# = Sin(p\Animation\Direction#)*1
				;		p\Motion\Speed\z# = -Cos(p\Animation\Direction#)*1
				;	EndIf
					
					If (ChannelPlaying(Channel_Grind)=False) Then
						Channel_Grind = PlaySnd(Sound_Grind)
					EndIf
					
					If (p\Motion\Ground = False) Then
						StopChannel(Channel_Grind)
					EndIf
					;Player_AdjustToRail(p)
				;	p\Motion\Speed\x# = TFormedX#()
				;	p\Motion\Speed\y# = TFormedY#()
				;	p\Motion\Speed\z# = TFormedZ#()
				;	MotionPressure#  = 0
					
					If (EntityCollided(p\Objects\R_GrindAffector, COLLISION_WORLD_RAIL)) Then
						p\Animation\Direction# = p\Animation\Direction# + (0.7*d\Delta)
					EndIf
					
					If (EntityCollided(p\Objects\L_GrindAffector, COLLISION_WORLD_RAIL)) Then
						p\Animation\Direction# = p\Animation\Direction# - (0.7*d\Delta)
					EndIf
					
					If (p\Action = ACTION_ROLL Or p\Action = ACTION_SLIDE) Then p\Action = ACTION_COMMON
					
					
				;	PointEntity p\Objects\RailPointer, p\Objects\Mesh
				;	p\Animation\Direction# = EntityY#(p\Objects\RailPointer)
					
				Case COLLISION_WORLD_INTERSECTTEST
					; Setup
			;		ShouldAlign = False
					
					Col = GetEntityType(CollisionEntity(p\Objects\Entity, i))
					
					; Acquire collided surface & triangle
					Surface 	= CollisionSurface(p\Objects\Entity, i)
					Triangle 	= CollisionTriangle(p\Objects\Entity, i)
					
					For j = 0 To 2
						Vertex		  	   = TriangleVertex(Surface, Triangle, j)
						CollisionNormal\x# = CollisionNormal\x#+VertexNX#(Surface, Vertex)
						CollisionNormal\y# = CollisionNormal\y#+VertexNY#(Surface, Vertex)
						CollisionNormal\z# = CollisionNormal\z#+VertexNZ#(Surface, Vertex)
					Next
					
			;		Vector_Normalize(CollisionNormal)
					
				;	If (MeshesIntersect(p\Objects\Mesh, Mesh)=True) Then
				;		If ChannelPlaying(Channel_Error)=False Then Channel_Error = PlaySound(Sound_Error)
				;	EndIf
					
					; Calculate dot product
			;		DotProduct# = Vector_DotProduct#(CollisionNormal, Game\Stage\GravityAlignment)
					
				Case COLLISION_WORLD_CAM
					; Setup
			;		ShouldAlign = False
					
					; Acquire collided surface & triangle
					Surface 	= CollisionSurface(p\Objects\Entity, i)
					Triangle 	= CollisionTriangle(p\Objects\Entity, i)
					
					For j = 0 To 2
						Vertex		  	   = TriangleVertex(Surface, Triangle, j)
						CollisionNormal\x# = CollisionNormal\x#+VertexNX#(Surface, Vertex)
						CollisionNormal\y# = CollisionNormal\y#+VertexNY#(Surface, Vertex)
						CollisionNormal\z# = CollisionNormal\z#+VertexNZ#(Surface, Vertex)
					Next
					
			;		For k = 1 To CountCollisions(p\Objects\Entity)
						
			;			Select GetEntityType(CollisionEntity(p\Objects\WallCubeDetect, k))
			;					COLLISION_WORLD_CAM
								
						
			;		Vector_Normalize(CollisionNormal)
					
			;		If (MeshesIntersect(p\Objects\Mesh, Mesh)=True) Then
			;			If ChannelPlaying(Channel_Error)=False Then Channel_Error = PlaySound(Sound_Error)
			;		EndIf
					
					; Calculate dot product
			;		DotProduct# = Vector_DotProduct#(CollisionNormal, Game\Stage\GravityAlignment)
					
			End Select
			
			;Col = GetEntityType(CollisionEntity(p\Objects\Entity, i))
			
			If Col = COLLISION_WORLD_RAIL
				p\Grinding = 1
		;		MOTION_GROUND# = 0.55
		;		MOTION_WALL_UP#	= -0.7
			Else
				p\Grinding = 0
		;		MOTION_GROUND# = 0.65
		;		MOTION_WALL_UP#	= -0.7
			EndIf
			
;			For k = 1 To CountCollisions(p\Objects\WallCubeDetect)
;				Select GetEntityType(CollisionEntity(p\Objects\Entity, k))
;					Case COLLISION_WORLD_LEDGE
;						If (p\Action <> ACTION_GLIDE And p\Motion\Speed\y# < 0.0) Then
;				p\Action = ACTION_LEDGE
;				p\Motion\Speed\x# = 0
;				p\Motion\Speed\y# = 0
;				p\Motion\Speed\z# = 0
;			EndIf
;	End Select
			
;		Next
			

			; Test for ground collision
			If (DotProduct# > GroundTest# And (p\Motion\Speed\y#<=0.0 Or p\Motion\Ground)) Then
				If (ShouldAlign = True) Then
					Align = True
					Vector_Add(GroundNormal, CollisionNormal)
				End If
				GroundTest#	= DotProduct#
				
			End If

			; Test for ground collision
			If (DotProduct# < CeilingTest# And p\Motion\Speed\y#>0.0) Then
				Vector_Add(CeilingNormal, CollisionNormal)
				CeilingTest# = DotProduct#
			End If

			; Test for front collision. 
			If (DotProduct#>=MOTION_WALL_UP# And DotProduct#<=MOTION_WALL_DOWN#) Then
				; Even though the dot product told us there was a collision, it may have been
				; anywhere surrounding the player. Check out the orientation of the collision
				; with a cross product.
				Cross.tVector = Null
				
				Vector_CrossProduct(CollisionNormal, p\Motion\Align, CollisionNormal)
				DotProduct# = 1-Abs(Vector_DotProduct#(CollisionNormal, SpeedNormal))

				; Finally, test for front collision
				If (DotProduct#>FrontTest#) Then
					FrontTest# = DotProduct#
				End If
			End If
		Next

		; If there was a collision in the front, calculate how much the speed should drop.
		If (FrontTest#>MOTION_WALL_DIRECTON#) Then
			FrontFactor# = 1-Min#(((FrontTest#-MOTION_WALL_DIRECTION#)/(1-MOTION_WALL_DIRECTION#))*d\Delta*1.2, 1.0)
			p\Motion\Speed\x# = p\Motion\Speed\x#*FrontFactor#
			p\Motion\Speed\z# = p\Motion\Speed\z#*FrontFactor#
		End If

		; Once we know there's ground collision for sure, change alignment.
		If (GroundTest# > MOTION_GROUND#) Then
			If (Align = True) Then
				Vector_Normalize(GroundNormal)
				Vector_SetFromVector(p\Motion\Align, GroundNormal)
			Else
				Vector_SetFromVector(p\Motion\Align, Game\Stage\GravityAlignment)
			End If
			Result = True
		; If no ground collision was found, maybe there's ceiling collision
		Else If (CeilingTest# < MOTION_CEILING#) Then
			; If the ceiling slope is low enough, make Sonic land on the
			; ceiling.
			If (CeilingTest# < MOTION_CEILING_STOP#) Then
				p\Motion\Speed\y# = 0
				Result = False
			
			; If not, adjust to new alignment
			Else
				Vector_Normalize(CeilingNormal)
				Vector_SetFromVector(p\Motion\Align, CeilingNormal)
				Result = True
			End If
		Else
			Vector_SetFromVector(p\Motion\Align, Game\Stage\GravityAlignment)
			Result = False
		End If

		Delete CollisionNormal : Delete GroundNormal : Delete CeilingNormal : Delete SpeedNormal
		Return Result

	End Function


	; =========================================================================================================
	; Player_Align
	; =========================================================================================================
	Function Player_Align(p.tPlayer)
		RotateEntity(p\Objects\Entity, 0, 0, 0)
		AlignToVector(p\Objects\Entity, p\Motion\Align\x#, p\Motion\Align\y#, p\Motion\Align\z#, 2)
		
		If (p\Motion\Align\y# = 180) Then p\Animation\Direction# = EntityYaw#(p\Objects\Mesh)
	End Function


	; =========================================================================================================
	; Player_ConvertAirToGround
	; =========================================================================================================
	Function Player_ConvertAirToGround(p.tPlayer)
		TFormVector(p\Motion\Speed\x#, p\Motion\Speed\y#, p\Motion\Speed\z#, Game\Stage\Gravity, p\Objects\Entity)
		p\Motion\Speed\x# = TFormedX#()
		p\Motion\Speed\y# = 0
		p\Motion\Speed\z# = TFormedZ#()
	End Function


	; =========================================================================================================
	; Player_ConvertGroundToAir
	; =========================================================================================================
Function Player_ConvertGroundToAir(p.tPlayer)
	p\FallTimer = MilliSecs() + 3500
	
		TFormVector(p\Motion\Speed\x#, p\Motion\Speed\y#, p\Motion\Speed\z#, p\Objects\Entity, Game\Stage\Gravity)
		p\Motion\Speed\x# = TFormedX#()
		p\Motion\Speed\y# = TFormedY#()
		p\Motion\Speed\z# = TFormedZ#()
End Function

	; =========================================================================================================
	; Player_ConvertGroundToDashPad
	; =========================================================================================================
Function Player_ConvertGroundToDashPad(p.tPlayer, o.tObject)
	TFormVector(p\Motion\Speed\x#, p\Motion\Speed\y#, p\Motion\Speed\z#, p\Objects\Entity, o\Entity)
;	AlignToVector(p\Objects\Entity, ex#, ey#, ez#, 2, .80)
;	AlignToVector(p\Objects\Mesh, ex#, ey#, ez#, 2, .80)
;	p\Motion\Speed\x# = TFormedX#()
;	p\Motion\Speed\y# = 0
;	p\Motion\Speed\z# = TFormedZ#()
End Function

	; =========================================================================================================
	; Player_ConvertGroundToRoll
	; =========================================================================================================
Function Player_ConvertGroundToRoll(p.tPlayer)
	If p\Motion\Align\x#<>0 Or p\Motion\Align\z#<>0 Then
		TFormVector(p\Motion\Align\x#, p\Motion\Speed\y#, p\Motion\Align\z#, Game\Stage\Gravity, p\Objects\Entity)
		
		; X
		If TFormedX#() < 0.0 Then
			If p\Motion\Speed\x# > 0.0 Then
				p\Motion\Speed\x# = p\Motion\Speed\x# + (TFormedX#()*COMMON_ROLLWEIGHT_UP#)
			End If
			If p\Motion\Speed\x# < 0.0 Then
				p\Motion\Speed\x# = p\Motion\Speed\x# + (TFormedX#()*COMMON_ROLLWEIGHT_DOWN# * ROLL_WEIGHT_MULTIPLIER#)
			End If
		End If
		
		If TFormedX#() > 0.0 Then
			If p\Motion\Speed\x# < 0.0 Then
				p\Motion\Speed\x# = p\Motion\Speed\x# + (TFormedX#()*COMMON_ROLLWEIGHT_UP#)
			End If
			If p\Motion\Speed\x# > 0.0 Then
				p\Motion\Speed\x# = p\Motion\Speed\x# + (TFormedX#()*COMMON_ROLLWEIGHT_DOWN# * ROLL_WEIGHT_MULTIPLIER#)
			End If
		End If
		
		; Z
		If TFormedZ#() < 0.0 Then
			If p\Motion\Speed\z# > 0.0 Then
				p\Motion\Speed\z# = p\Motion\Speed\z# + (TFormedZ#()*COMMON_ROLLWEIGHT_UP#)
			End If
			If p\Motion\Speed\z# < 0.0 Then
				p\Motion\Speed\z# = p\Motion\Speed\z# + (TFormedZ#()*COMMON_ROLLWEIGHT_DOWN# * ROLL_WEIGHT_MULTIPLIER#)
			End If
		End If
		
		If TFormedZ#() > 0.0 Then
			If p\Motion\Speed\z# < 0.0 Then
				p\Motion\Speed\z# = p\Motion\Speed\z# + (TFormedZ#()*COMMON_ROLLWEIGHT_UP#)
			End If
			If p\Motion\Speed\z# > 0.0 Then
				p\Motion\Speed\z# = p\Motion\Speed\z# + (TFormedZ#()*COMMON_ROLLWEIGHT_DOWN# * ROLL_WEIGHT_MULTIPLIER#)
			End If
		End If
		
	End If
End Function

	; =========================================================================================================
	; Player_EaseMeshRot
	; =========================================================================================================
Function Player_EaseMeshRot(p.tPlayer, Obj, ChangeDir=1, Method=1, PX#=0, PY#=1, PZ#=0)
	Local Pivot = CreatePivot()
	Local Pointer = CreatePivot()
	;HideEntity Pointer
	
	EntityParent(Pivot, p\Objects\Mesh)
	;EntityParent(Pointer, p\Objects\Mesh)
	PositionEntity(Pointer, EntityX#(p\Objects\Mesh), EntityY#(p\Objects\Mesh), EntityZ#(p\Objects\Mesh))
	
	Local PPitch#	= EntityPitch#(Pointer)
	Local PYaw#		= EntityYaw#(Pointer)
	Local PRoll#	= EntityRoll#(Pointer)
	
	Local Pitch#	= EntityPitch#(p\Objects\Mesh)
	Local Yaw#		= EntityYaw#(p\Objects\Mesh)
	Local Roll#		= EntityRoll#(p\Objects\Mesh)
	
	Local OptX#
	Local OptY#
	Local OptZ#
	
	
	Select Method
		Case 1
			PointEntity(Pointer, Obj)
			For d.tDeltaTime = Each tDeltaTime
				OptX# = CountToValue#(Pitch#, -PPitch#, 5, d\Delta#)
				OptY# = CountToValue#(Yaw#, -PYaw#, 5, d\Delta#)
				OptZ# = CountToValue#(Roll#, -PRoll#, 5, d\Delta#)
		Next
			
			RotateEntity(p\Objects\Mesh, OptX#, OptY#, OptZ#)
			
		;	If (ChangeDir=1) Then p\Animation\Direction# = EntityYaw#(p\Objects\Mesh)
		Case 2
			MoveEntity(Pivot, PX#, PY#, PZ#)
			PointEntity(p\Objects\Mesh, Pivot)
		;	If (ChangeDir=1) Then p\Animation\Direction# = EntityYaw#(p\Objects\Mesh)
	End Select
End Function
;~IDEal Editor Parameters:
;~B#34#4D#65#76#86#88#8B#8D#8F#95#A4#C3#C4#C7#D2#D6#D8#E9#ED#100
;~B#12B#132#227#228#23C#24D#24F#251#253#25E#261#265#270#272#274#2A1#2A2
;~C#Blitz3D