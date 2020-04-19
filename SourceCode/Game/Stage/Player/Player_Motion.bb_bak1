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

	; ---- Motion routines ----
	; =========================================================================================================
	; Player_Motion
	; =========================================================================================================
	Function Player_Motion(p.tPlayer, d.tDeltaTime)
		; Test out collisions with scenery since last update. While doing this, calculate if character's on
		; ground, and if so, set the alignment.
		Ground = Player_TestCollisions(p, d)

		; Once we know if the character's on ground, check for the ground flag and change motion speed in
		; consecuence.
		Select Ground
			Case True
				Player_Align(p)
				
				If (p\Motion\Ground = False) Then					
					; If character just landed, transpose air speed to ground					
					Player_ConvertAirToGround(p)
					p\Motion\Ground = True

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
				
				Player_Align(p)
		End Select

		; Smoothly change animation alignment to the one of the player
		Vector_LinearInterpolation(p\Animation\Align, p\Motion\Align, (0.01+Vector_Length#(p\Motion\Speed)*0.07)*d\Delta#)
		Vector_Normalize(p\Animation\Align)

		; Change direction of the mesh
		PositionEntity(p\Objects\Mesh, EntityX(p\Objects\Entity), EntityY(p\Objects\Entity), EntityZ(p\Objects\Entity))
		RotateEntity(p\Objects\Mesh, 0, p\Animation\Direction#, 0)
		AlignToVector(p\Objects\Mesh, p\Animation\Align\x#, p\Animation\Align\y#, p\Animation\Align\z#, 2)

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
		
		; Now, just move over the character to the new position, based on it's speed.
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

				Case COLLISION_WORLD_BOX
					; Setup
					ShouldAlign = False
					
					CollisionNormal\x# = CollisionNX#(p\Objects\Entity, i)
					CollisionNormal\y# = CollisionNY#(p\Objects\Entity, i)
					CollisionNormal\z# = CollisionNZ#(p\Objects\Entity, i)

					; Calculate dot product
					DotProduct# = Vector_DotProduct#(CollisionNormal, p\Motion\Align)
			End Select

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
		TFormVector(p\Motion\Speed\x#, p\Motion\Speed\y#, p\Motion\Speed\z#, p\Objects\Entity, Game\Stage\Gravity)
		p\Motion\Speed\x# = TFormedX#()
		p\Motion\Speed\y# = TFormedY#()
		p\Motion\Speed\z# = TFormedZ#()
	End Function