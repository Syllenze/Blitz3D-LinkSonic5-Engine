; RottParticles by Rob W (rottbott@hotmail.com), July 2004

; To do --------------------------------------------------------------------------------------------------------------------
; -----/
; Particle bounce/destroy planes
; Delta scale

; Constants ----------------------------------------------------------------------------------------------------------------

; Emitter shapes
Const RP_Sphere   = 1
Const RP_Cylinder = 2
Const RP_Box      = 3

; Config velocities calculation modes
Const RP_Normal            = 1
Const RP_ShapeBased        = 2
Const RP_HeavilyShapeBased = 3

; Types --------------------------------------------------------------------------------------------------------------------

; An actual particle emitter
Type RP_Emitter
	Field Config1
	Field Emitter1
	Field MeshEN, EmitterEN
	Field Config.RP_EmitterConfig
	Field Enabled
	Field KillMode
	Field ToSpawn
	Field ActiveParticles
End Type

; A single particle (4 vertices)
Type RP_Particle
	Field Particle1
	Field E.RP_Emitter
	Field FirstVertex
	Field Scale#
	Field X#, Y#, Z#
	Field VX#, VY#, VZ#
	Field A#
	Field InUse
	Field TimeToLive#
	Field TexFrame, TexChange#
End Type

; A configuration template for emitters
Type RP_EmitterConfig
	Field Name$
	Field MaxParticles, ParticlesPerFrame
	Field Texture, TexAcross, TexDown, RndStartFrame, TexAnimSpeed
	Field VShapeBased
	Field VelocityX#, VelocityY#, VelocityZ#
	Field VelocityRndX#, VelocityRndY#, VelocityRndZ#
	Field ForceX#, ForceY#, ForceZ#
	Field ScaleStart#, ScaleChange#
	Field Lifespan
	Field AlphaStart#, AlphaChange#
	Field FaceEntity
	Field BlendMode
	Field Shape
	Field MinRadius#, MaxRadius#
	Field Width#, Height#, Depth#
	Field ShapeAxis
End Type

; Functions ----------------------------------------------------------------------------------------------------------------

; Updates all emitters
Function RP_Update(Delta# = 1.0)

	; Update emitters
	For E.RP_Emitter = Each RP_Emitter
		; Move mesh to new handle position - leaving them at 0, 0, 0 works but may work against Blitz's entity culling
		PositionEntity E\MeshEN, EntityX#(E\EmitterEN, True), EntityY#(E\EmitterEN, True), EntityZ#(E\EmitterEN, True)

		; Only update emitter if it's enabled
		If E\Enabled = True
			; Spawn new particles
			If E\KillMode = 0
				E\ToSpawn = Ceil(Float#(E\Config\ParticlesPerFrame) * Delta#)
			; If emitter is being killed, free it after all particles are dead
			ElseIf E\ActiveParticles = 0
				Select E\KillMode
					Case 1
						RP_FreeEmitter(E\EmitterEN, False, False)
					Case 2
						RP_FreeEmitter(E\EmitterEN, True, True)
					Case 3
						RP_FreeEmitter(E\EmitterEN, True, False)
					Case 4
						RP_FreeEmitter(E\EmitterEN, False, True)
				End Select
			EndIf
		; If it is disabled and has no particles, hide it
		Else
			If E\ActiveParticles = 0 Then HideEntity E\MeshEN
		EndIf
	Next

	; Update particles
	For P.RP_Particle = Each RP_Particle
		; Get surface for this particle
		Surf = GetSurface(P\E\MeshEN, 1)

		; If in use, update it
		If P\InUse = True
			; Update particle texture frame if necessary
			If P\TexChange# > 1.0
				P\TexChange# = P\TexChange# - Delta#
				If P\TexChange# <= 1.0
					Frame = P\TexFrame + 1
					If Frame > (P\E\Config\TexAcross * P\E\Config\TexDown) - 1 Then Frame = 0
					RP_SetParticleFrame(P, Frame)
				EndIf
			EndIf

			; Adjust velocity
			TFormVector P\E\Config\ForceX#, P\E\Config\ForceY#, P\E\Config\ForceZ#, P\E\EmitterEN, 0
			P\VX# = P\VX# + (TFormedX#() * Delta#)
			P\VY# = P\VY# + (TFormedY#() * Delta#)
			P\VZ# = P\VZ# + (TFormedZ#() * Delta#)

			; Move
			P\X# = P\X# + (P\VX# * Delta#)
			P\Y# = P\Y# + (P\VY# * Delta#)
			P\Z# = P\Z# + (P\VZ# * Delta#)

			; Update scale
			P\Scale# = P\Scale# + (P\E\Config\ScaleChange# * Delta#)

			; Update vertex positions
			RP_UpdateParticleVertices(P)

			; Count down lifespan
			P\TimeToLive# = P\TimeToLive# - Delta#

			; Adjust alpha
			P\A# = P\A# + (P\E\Config\AlphaChange# * Delta#)
			If P\A# <= 0.0 Then P\TimeToLive# = -1.0
			RP_ParticleAlpha(Surf, P\FirstVertex, P\A#)

			; Lifespan is up
			If P\TimeToLive# < 0.0
				P\E\ActiveParticles = P\E\ActiveParticles - 1
				; Respawn
				If P\E\ToSpawn > 0
					RP_SpawnParticle(P)
				; More particles not needed - turn invisible until required
				Else
					P\InUse = False
					RP_ParticleAlpha(Surf, P\FirstVertex, 0.0)
				EndIf
			EndIf

		; If it is not in use, and the emitter still needs to spawn some, then spawn it
		Else
			If P\E\ToSpawn > 0 Then RP_SpawnParticle(P)
		EndIf
	Next

End Function

; Adds a new particle to an emitter
Function RP_CreateParticle(E.RP_Emitter)

	Surf = GetSurface(E\MeshEN, 1)
	V1 = AddVertex(Surf, 0.0, 0.0, 0.0, 0.0, 1.0)
	V2 = AddVertex(Surf, 0.0, 0.0, 0.0, 0.0, 0.0)
	V3 = AddVertex(Surf, 0.0, 0.0, 0.0, 1.0, 0.0)
	V4 = AddVertex(Surf, 0.0, 0.0, 0.0, 1.0, 1.0)
	VertexColor(Surf, V1, 255, 255, 255, 0.0)
	VertexColor(Surf, V2, 255, 255, 255, 0.0)
	VertexColor(Surf, V3, 255, 255, 255, 0.0)
	VertexColor(Surf, V4, 255, 255, 255, 0.0)

	AddTriangle(Surf, V1, V2, V3)
	AddTriangle(Surf, V1, V3, V4)

	P.RP_Particle = New RP_Particle
	P\E = E
	P\FirstVertex = V1
	P\InUse = False

End Function

; Spawns a particle
Function RP_SpawnParticle(P.RP_Particle)

	Surf = GetSurface(P\E\MeshEN, 1)
	P\InUse = True
	P\E\ActiveParticles = P\E\ActiveParticles + 1

	; Plain initial velocities
	VX# = P\E\Config\VelocityX# + Rnd#(-P\E\Config\VelocityRndX#, P\E\Config\VelocityRndX#)
	VY# = P\E\Config\VelocityY# + Rnd#(-P\E\Config\VelocityRndY#, P\E\Config\VelocityRndY#)
	VZ# = P\E\Config\VelocityZ# + Rnd#(-P\E\Config\VelocityRndZ#, P\E\Config\VelocityRndZ#)

	; Initial position (and changed velocities if they are "shape based")
	Select P\E\Config\Shape
		; Sphere shape
		Case RP_Sphere
			; Position
			Distance# = Rnd#(P\E\Config\MinRadius#, P\E\Config\MaxRadius#)
			Pitch# = Rnd#(-90.0, 90.0)
			Yaw# = Rnd#(-180.0, 180.0)
			P\Y# = EntityY#(P\E\EmitterEN, True) + (Sin#(Pitch#) * Distance#)
			FDistance# = Cos#(Pitch#) * Distance#
			P\X# = EntityX#(P\E\EmitterEN, True) + (Cos#(Yaw#) * FDistance#)
			P\Z# = EntityZ#(P\E\EmitterEN, True) + (Sin#(Yaw#) * FDistance#)

			; Velocity
			If P\E\Config\VShapeBased = RP_ShapeBased
				VX# = Abs(VX#) * Sgn(Cos#(Yaw#) * FDistance#)
				VY# = Abs(VY#) * Sgn(Sin#(Pitch#) * Distance#)
				VZ# = Abs(VZ#) * Sgn(Sin#(Yaw#) * FDistance#)
			ElseIf P\E\Config\VShapeBased = RP_HeavilyShapeBased
				VX# = Abs(VX#) * ((Cos#(Yaw#) * FDistance#) / P\E\Config\MaxRadius#)
				VY# = Abs(VY#) * ((Sin#(Pitch#) * Distance#) / P\E\Config\MaxRadius#)
				VZ# = Abs(VZ#) * ((Sin#(Yaw#) * FDistance#) / P\E\Config\MaxRadius#)
			EndIf
		; Cylinder shape
		Case RP_Cylinder
			; Position
			Distance# = Rnd#(P\E\Config\MinRadius#, P\E\Config\MaxRadius#)
			Yaw# = Rnd#(-180.0, 180.0)
			Height# = Rnd#((P\E\Config\Depth#) / -2.0, (P\E\Config\Depth#) / 2.0)
			Select P\E\Config\ShapeAxis
				; Cylinder lies along X axis
				Case 1
					P\X# = EntityX#(P\E\EmitterEN, True) + Height#
					P\Y# = EntityY#(P\E\EmitterEN, True) + (Cos#(Yaw#) * Distance#)
					P\Z# = EntityZ#(P\E\EmitterEN, True) + (Sin#(Yaw#) * Distance#)
				; Cylinder lies along Y axis
				Case 2
					P\Y# = EntityY#(P\E\EmitterEN, True) + Height#
					P\X# = EntityX#(P\E\EmitterEN, True) + (Cos#(Yaw#) * Distance#)
					P\Z# = EntityZ#(P\E\EmitterEN, True) + (Sin#(Yaw#) * Distance#)
				; Cylinder lies along Z axis
				Case 3
					P\Z# = EntityZ#(P\E\EmitterEN, True) + Height#
					P\X# = EntityX#(P\E\EmitterEN, True) + (Cos#(Yaw#) * Distance#)
					P\Y# = EntityY#(P\E\EmitterEN, True) + (Sin#(Yaw#) * Distance#)
			End Select

			; Velocity
			If P\E\Config\VShapeBased = RP_ShapeBased
				Select P\E\Config\ShapeAxis
					; Cylinder lies along X axis
					Case 1
						VX# = Abs(VX#) * Sgn(Height#)
						VY# = Abs(VY#) * Sgn(Cos#(Yaw#) * Distance#)
						VZ# = Abs(VZ#) * Sgn(Sin#(Yaw#) * Distance#)
					; Cylinder lies along Y axis
					Case 2
						VY# = Abs(VY#) * Sgn(Height#)
						VX# = Abs(VX#) * Sgn(Cos#(Yaw#) * Distance#)
						VZ# = Abs(VZ#) * Sgn(Sin#(Yaw#) * Distance#)
					; Cylinder lies along Z axis
					Case 3
						VZ# = Abs(VZ#) * Sgn(Height#)
						VX# = Abs(VX#) * Sgn(Cos#(Yaw#) * Distance#)
						VY# = Abs(VY#) * Sgn(Sin#(Yaw#) * Distance#)
				End Select
			ElseIf P\E\Config\VShapeBased = RP_HeavilyShapeBased
				Select P\E\Config\ShapeAxis
					; Cylinder lies along X axis
					Case 1
						VX# = 0.0
						VY# = Abs(VY#) * ((Cos#(Yaw#) * Distance#) / P\E\Config\MaxRadius#)
						VZ# = Abs(VZ#) * ((Sin#(Yaw#) * Distance#) / P\E\Config\MaxRadius#)
					; Cylinder lies along Y axis
					Case 2
						VY# = 0.0
						VX# = Abs(VX#) * ((Cos#(Yaw#) * Distance#) / P\E\Config\MaxRadius#)
						VZ# = Abs(VZ#) * ((Sin#(Yaw#) * Distance#) / P\E\Config\MaxRadius#)
					; Cylinder lies along Z axis
					Case 3
						VZ# = 0.0
						VX# = Abs(VX#) * ((Cos#(Yaw#) * Distance#) / P\E\Config\MaxRadius#)
						VY# = Abs(VY#) * ((Sin#(Yaw#) * Distance#) / P\E\Config\MaxRadius#)
				End Select
			EndIf
		; Box shape
		Case RP_Box
			; Position
			X# = Rnd#(P\E\Config\Width# / -2.0, P\E\Config\Width# / 2.0)
			Y# = Rnd#(P\E\Config\Height# / -2.0, P\E\Config\Height# / 2.0)
			Z# = Rnd#(P\E\Config\Depth# / -2.0, P\E\Config\Depth# / 2.0)
			P\X# = EntityX#(P\E\EmitterEN, True) + X#
			P\Y# = EntityY#(P\E\EmitterEN, True) + Y#
			P\Z# = EntityZ#(P\E\EmitterEN, True) + Z#

			; Velocity
			If P\E\Config\VShapeBased = RP_ShapeBased
				VX# = Abs(VX#) * Sgn(X#)
				VY# = Abs(VY#) * Sgn(Y#)
				VZ# = Abs(VZ#) * Sgn(Z#)
			ElseIf P\E\Config\VShapeBased = RP_HeavilyShapeBased
				X# = X# / (P\E\Config\Width# / 2.0)
				Y# = Y# / (P\E\Config\Height# / 2.0)
				Z# = Z# / (P\E\Config\Depth# / 2.0)
				If Abs(X#) > Abs(Y#) And Abs(X#) > Abs(Z#)
					VX# = Abs(VX#) * Sgn(X#)
					VY# = 0.0
					VZ# = 0.0
				ElseIf Abs(Y#) > Abs(X#) And Abs(Y#) > Abs(Z#)
					VY# = Abs(VY#) * Sgn(Y#)
					VX# = 0.0
					VZ# = 0.0
				Else
					VZ# = Abs(VZ#) * Sgn(Z#)
					VX# = 0.0
					VY# = 0.0
				EndIf
			EndIf
	End Select

	; Transform velocities to global space (so emitter shapes can be rotated)
	TFormVector VX#, VY#, VZ#, P\E\EmitterEN, 0
	P\VX# = TFormedX#()
	P\VY# = TFormedY#()
	P\VZ# = TFormedZ#()

	; Initial texture frame
	If P\E\Config\RndStartFrame = True
		RP_SetParticleFrame(P, Rand(0, (P\E\Config\TexAcross * P\E\Config\TexDown) - 1))
	Else
		RP_SetParticleFrame(P, 0)
	EndIf

	; Other bits
	RP_ParticleAlpha(Surf, P\FirstVertex, 0.0)
	P\Scale# = P\E\Config\ScaleStart#
	P\A# = P\E\Config\AlphaStart#
	P\TimeToLive# = P\E\Config\Lifespan

	P\E\ToSpawn = P\E\ToSpawn - 1

End Function

; Sets the texture frame for a particle
Function RP_SetParticleFrame(P.RP_Particle, Frame)

	X = Frame Mod P\E\Config\TexAcross
	If Frame > P\E\Config\TexAcross - 1
		Y = ((Frame - X) / (P\E\Config\TexAcross - 1)) - 1
	Else
		Y = 0
	EndIf

	MinU# = (1.0 / Float#(P\E\Config\TexAcross)) * Float#(X)
	MaxU# = (1.0 / Float#(P\E\Config\TexAcross)) * Float#(X + 1)
	MinV# = (1.0 / Float#(P\E\Config\TexDown)) * Float#(Y)
	MaxV# = (1.0 / Float#(P\E\Config\TexDown)) * Float#(Y + 1)

	Surf = GetSurface(P\E\MeshEN, 1)

	VertexTexCoords Surf, P\FirstVertex, MinU#, MaxV#
	VertexTexCoords Surf, P\FirstVertex + 1, MinU#, MinV#
	VertexTexCoords Surf, P\FirstVertex + 2, MaxU#, MinV#
	VertexTexCoords Surf, P\FirstVertex + 3, MaxU#, MaxV#

	P\TexFrame = Frame
	P\TexChange# = P\E\Config\TexAnimSpeed + 1

End Function

; Updates particle vertices to new position (and makes each particle face camera)
Function RP_UpdateParticleVertices(P.RP_Particle)

	Surf = GetSurface(P\E\MeshEN, 1)

	; Adjust for mesh position
	PosX# = P\X# - EntityX#(P\E\MeshEN, True)
	PosY# = P\Y# - EntityY#(P\E\MeshEN, True)
	PosZ# = P\Z# - EntityZ#(P\E\MeshEN, True)

	; Get the new position of each corner vertex
	TFormVector -P\Scale#, -P\Scale#, 0, P\E\Config\FaceEntity, 0
	VertexCoords Surf, P\FirstVertex, PosX# + TFormedX#(), PosY# + TFormedY#(), PosZ# + TFormedZ#()

	TFormVector -P\Scale#, P\Scale#, 0, P\E\Config\FaceEntity, 0
	VertexCoords Surf, P\FirstVertex + 1, PosX# + TFormedX#(), PosY# + TFormedY#(), PosZ# + TFormedZ#()

	TFormVector P\Scale#, P\Scale#, 0, P\E\Config\FaceEntity, 0
	VertexCoords Surf, P\FirstVertex + 2, PosX# + TFormedX#(), PosY# + TFormedY#(), PosZ# + TFormedZ#()

	TFormVector P\Scale#, -P\Scale#, 0, P\E\Config\FaceEntity, 0
	VertexCoords Surf, P\FirstVertex + 3, PosX# + TFormedX#(), PosY# + TFormedY#(), PosZ# + TFormedZ#()

End Function

; Sets the alpha of a particle
Function RP_ParticleAlpha(Surf, FirstVertex, A#)

	VertexColor Surf, FirstVertex,     255, 255, 255, A#
	VertexColor Surf, FirstVertex + 1, 255, 255, 255, A#
	VertexColor Surf, FirstVertex + 2, 255, 255, 255, A#
	VertexColor Surf, FirstVertex + 3, 255, 255, 255, A#

End Function

; Changes the particle lifespan of a config
Function RP_ConfigLifespan(ID, Lifespan)

	; Find config
	C.RP_EmitterConfig = Object.RP_EmitterConfig(ID)
	If C <> Null
		C\Lifespan = Lifespan
		Return True
	Else
		Return False
	EndIf

End Function

; Changes the particles generated per frame of a config
Function RP_ConfigSpawnRate(ID, SpawnRate)

	; Find config
	C.RP_EmitterConfig = Object.RP_EmitterConfig(ID)
	If C <> Null
		C\ParticlesPerFrame = SpawnRate
		Return True
	Else
		Return False
	EndIf

End Function

; Sets the initial velocites to "shape based", "heavily shape based", or "normal" for a config
Function RP_ConfigVelocityMode(ID, Level)

	; Find config
	C.RP_EmitterConfig = Object.RP_EmitterConfig(ID)
	If C <> Null
		C\VShapeBased = Level
		Return True
	Else
		Return False
	EndIf

End Function

; Changes the initial particle X-velocity of a config
Function RP_ConfigVelocityX(ID, VelocityX#)

	; Find config
	C.RP_EmitterConfig = Object.RP_EmitterConfig(ID)
	If C <> Null
		C\VelocityX# = VelocityX#
		Return True
	Else
		Return False
	EndIf

End Function

; Changes the initial particle Y-velocity of a config
Function RP_ConfigVelocityY(ID, VelocityY#)

	; Find config
	C.RP_EmitterConfig = Object.RP_EmitterConfig(ID)
	If C <> Null
		C\VelocityY# = VelocityY#
		Return True
	Else
		Return False
	EndIf

End Function

; Changes the initial particle Z-velocity of a config
Function RP_ConfigVelocityZ(ID, VelocityZ#)

	; Find config
	C.RP_EmitterConfig = Object.RP_EmitterConfig(ID)
	If C <> Null
		C\VelocityZ# = VelocityZ#
		Return True
	Else
		Return False
	EndIf

End Function

; Changes the initial particle X-velocity randomness of a config
Function RP_ConfigVelocityRndX(ID, VelocityX#)

	; Find config
	C.RP_EmitterConfig = Object.RP_EmitterConfig(ID)
	If C <> Null
		C\VelocityRndX# = VelocityX#
		Return True
	Else
		Return False
	EndIf

End Function

; Changes the initial particle Y-velocity randomness of a config
Function RP_ConfigVelocityRndY(ID, VelocityY#)

	; Find config
	C.RP_EmitterConfig = Object.RP_EmitterConfig(ID)
	If C <> Null
		C\VelocityRndY# = VelocityY#
		Return True
	Else
		Return False
	EndIf

End Function

; Changes the initial particle Z-velocity randomness of a config
Function RP_ConfigVelocityRndZ(ID, VelocityZ#)

	; Find config
	C.RP_EmitterConfig = Object.RP_EmitterConfig(ID)
	If C <> Null
		C\VelocityRndZ# = VelocityZ#
		Return True
	Else
		Return False
	EndIf

End Function

; Changes the delta particle X-velocity of a config
Function RP_ConfigForceX(ID, VelocityX#)

	; Find config
	C.RP_EmitterConfig = Object.RP_EmitterConfig(ID)
	If C <> Null
		C\ForceX# = VelocityX#
		Return True
	Else
		Return False
	EndIf

End Function

; Changes the delta particle Y-velocity of a config
Function RP_ConfigForceY(ID, VelocityY#)

	; Find config
	C.RP_EmitterConfig = Object.RP_EmitterConfig(ID)
	If C <> Null
		C\ForceY# = VelocityY#
		Return True
	Else
		Return False
	EndIf

End Function

; Changes the delta particle Z-velocity of a config
Function RP_ConfigForceZ(ID, VelocityZ#)

	; Find config
	C.RP_EmitterConfig = Object.RP_EmitterConfig(ID)
	If C <> Null
		C\ForceZ# = VelocityZ#
		Return True
	Else
		Return False
	EndIf

End Function

; Changes the initial particle alpha of a config
Function RP_ConfigInitialAlpha(ID, Alpha#)

	; Find config
	C.RP_EmitterConfig = Object.RP_EmitterConfig(ID)
	If C <> Null
		C\AlphaStart# = Alpha#
		Return True
	Else
		Return False
	EndIf

End Function

; Changes the delta particle alpha of a config
Function RP_ConfigAlphaChange(ID, Alpha#)

	; Find config
	C.RP_EmitterConfig = Object.RP_EmitterConfig(ID)
	If C <> Null
		C\AlphaChange# = Alpha#
		Return True
	Else
		Return False
	EndIf

End Function

; Changes the entity which particles face towards for a config
Function RP_ConfigFaceEntity(ID, Entity)

	; Find config
	C.RP_EmitterConfig = Object.RP_EmitterConfig(ID)
	If C <> Null
		C\FaceEntity = Entity
		Return True
	Else
		Return False
	EndIf

End Function

; Changes the blend mode for a config
Function RP_ConfigBlendMode(ID, Blend)

	; Find config
	C.RP_EmitterConfig = Object.RP_EmitterConfig(ID)
	If C <> Null
		C\BlendMode = Blend
		; For each emitter with this config, alter blend mode
		For E.RP_Emitter = Each RP_Emitter
			If E\Config = C Then EntityBlend E\MeshEN, Blend
		Next
		Return True
	Else
		Return False
	EndIf

End Function

; Changes the max number of particles for a config
Function RP_ConfigMaxParticles(ID, MaxParticles)

	; Find config
	C.RP_EmitterConfig = Object.RP_EmitterConfig(ID)
	If C <> Null
		Difference = MaxParticles - C\MaxParticles
		C\MaxParticles = MaxParticles

		; For each emitter with this config, create/destroy particles as required
		For E.RP_Emitter = Each RP_Emitter
			If E\Config = C
				; Add more particles
				If Difference > 0
					For i = 1 To Difference
						RP_CreateParticle(E)
					Next
				; Remove particles (rebuild emitter mesh)
				ElseIf Difference < 0
					ClearSurface(GetSurface(E\MeshEN, 1))
					For i = 1 To C\MaxParticles
						RP_CreateParticle(E)
					Next
				EndIf
			EndIf
		Next

		Return True
	Else
		Return False
	EndIf

End Function

; Changes the initial particle scale of a config
Function RP_ConfigInitialScale(ID, Scale#)

	; Find config
	C.RP_EmitterConfig = Object.RP_EmitterConfig(ID)
	If C <> Null
		C\ScaleStart# = Scale#
		Return True
	Else
		Return False
	EndIf

End Function

; Changes the particle scale change of a config
Function RP_ConfigScaleChange(ID, Scale#)

	; Find config
	C.RP_EmitterConfig = Object.RP_EmitterConfig(ID)
	If C <> Null
		C\ScaleChange# = Scale#
		Return True
	Else
		Return False
	EndIf

End Function

; Changes the texture of a config
Function RP_ConfigTexture(ID, Texture, TilesX, TilesY, FreePreviousTexture = True)

	; Find config
	C.RP_EmitterConfig = Object.RP_EmitterConfig(ID)
	If C <> Null
		; Update texture
		If FreePreviousTexture = True And C\Texture <> 0 Then FreeTexture C\Texture
		C\Texture = Texture
		C\TexAcross = TilesX
		C\TexDown = TilesY

		; For each emitter with this config, change the texture
		For E.RP_Emitter = Each RP_Emitter
			If E\Config = C
				EntityTexture E\MeshEN, C\Texture
			EndIf
		Next

		Return True
	Else
		Return False
	EndIf

End Function

; Changes the texture animation speed for a config
Function RP_ConfigTextureAnimSpeed(ID, Speed)

	; Find config
	C.RP_EmitterConfig = Object.RP_EmitterConfig(ID)
	If C <> Null
		C\TexAnimSpeed = Speed
		Return True
	Else
		Return False
	EndIf

End Function

; Switches config between random texture start frame and 0 texture start frame
Function RP_ConfigTextureRandomStartFrame(ID, Flag)

	; Find config
	C.RP_EmitterConfig = Object.RP_EmitterConfig(ID)
	If C <> Null
		C\RndStartFrame = Flag
		Return True
	Else
		Return False
	EndIf

End Function

; Sets an emitter config to a sphere shape (default)
Function RP_ConfigShapeSphere(ID, MinRadius# = 0.0, MaxRadius# = 0.0)

	C.RP_EmitterConfig = Object.RP_EmitterConfig(ID)
	If C <> Null
		C\Shape = RP_Sphere
		C\MinRadius# = MinRadius#
		C\MaxRadius# = MaxRadius#
		Return True
	Else
		Return False
	EndIf

End Function

; Sets an emitter config to a cylinder shape
Function RP_ConfigShapeCylinder(ID, MinRadius#, MaxRadius#, Length#, Axis)

	C.RP_EmitterConfig = Object.RP_EmitterConfig(ID)
	If C <> Null
		C\Shape = RP_Cylinder
		C\MinRadius# = MinRadius#
		C\MaxRadius# = MaxRadius#
		C\Depth# = Length#
		C\ShapeAxis = Axis
		Return True
	Else
		Return False
	EndIf

End Function

; Sets an emitter config to a box shape
Function RP_ConfigShapeBox(ID, Width#, Height#, Depth#)

	C.RP_EmitterConfig = Object.RP_EmitterConfig(ID)
	If C <> Null
		C\Shape = RP_Box
		C\Width# = Width#
		C\Height# = Height#
		C\Depth# = Depth#
		Return True
	Else
		Return False
	EndIf

End Function

; Creates a new emitter configuration and returns its ID
Function RP_CreateEmitterConfig(MaxParticles, SpawnRate, FaceEntity, Texture, TilesX = 1, TilesY = 1, Name$ = "New Emitter")

	; Create config with default values
	C.RP_EmitterConfig = New RP_EmitterConfig
	C\FaceEntity = FaceEntity
	C\AlphaStart# = 1.0
	C\Texture = Texture
	C\TexAcross = TilesX
	C\TexDown = TilesY
	C\RndStartFrame = True
	C\MaxParticles = MaxParticles
	C\ParticlesPerFrame = SpawnRate
	C\Lifespan = 100
	C\BlendMode = 3
	C\ScaleStart# = 1.0
	C\VShapeBased = RP_Normal
	C\Shape = RP_Sphere
	C\Name$ = Name$
	Return Handle(C)

End Function

; Copies an emitter configuration exactly
Function RP_CopyEmitterConfig(ID)

	C.RP_EmitterConfig = Object.RP_EmitterConfig(ID)
	If C <> Null
		C2.RP_EmitterConfig = New RP_EmitterConfig
		C2\MaxParticles = C\MaxParticles
		C2\ParticlesPerFrame = C\ParticlesPerFrame
		C2\Texture = C\Texture
		C2\TexAcross = C\TexAcross
		C2\TexDown = C\TexDown
		C2\RndStartFrame = C\RndStartFrame
		C2\TexAnimSpeed = C\TexAnimSpeed
		C2\VShapeBased = C\VShapeBased
		C2\VelocityX# = C\VelocityX#
		C2\VelocityY# = C\VelocityY#
		C2\VelocityZ# = C\VelocityZ#
		C2\VelocityRndX# = C\VelocityRndX#
		C2\VelocityRndY# = C\VelocityRndY#
		C2\VelocityRndZ# = C\VelocityRndZ#
		C2\ForceX# = C\ForceX#
		C2\ForceY# = C\ForceY#
		C2\ForceZ# = C\ForceZ#
		C2\ScaleStart# = C\ScaleStart#
		C2\ScaleChange# = C\ScaleChange#
		C2\Lifespan = C\Lifespan
		C2\AlphaStart# = C\AlphaStart#
		C2\AlphaChange# = C\AlphaChange#
		C2\FaceEntity = C\FaceEntity
		C2\BlendMode = C\BlendMode
		C2\Shape = C\Shape
		C2\MinRadius# = C\MinRadius#
		C2\MaxRadius# = C\MaxRadius#
		C2\Width# = C\Width#
		C2\Height# = C\Height#
		C2\Depth# = C\Depth#
		C2\ShapeAxis = C\ShapeAxis
		C2\Name$ = "Copied Emitter"
		Return Handle(C2)
	Else
		Return False
	EndIf

End Function

; Frees an emitter configuration
Function RP_FreeEmitterConfig(ID, FreeTex)

	C.RP_EmitterConfig = Object.RP_EmitterConfig(ID)
	If C <> Null
		If FreeTex = True
			; Free texture, making sure that all instances of this texture in the particle system are set to 0
			For C2.RP_EmitterConfig = Each RP_EmitterConfig
				If C2 <> C And C2\Texture = C\Texture Then C2\Texture = 0
			Next
			FreeTexture C\Texture
			C\Texture = 0
		EndIf
		Delete C
		Return True
	Else
		Return False
	EndIf

End Function

; Saves an emitter configuration to file
Function RP_SaveEmitterConfig(ID, File$)

	C.RP_EmitterConfig = Object.RP_EmitterConfig(ID)
	If C <> Null
		F = WriteFile(File$)
		If F = 0 Then Return False

			WriteInt F, C\MaxParticles
			WriteInt F, C\ParticlesPerFrame
			WriteInt F, C\TexAcross
			WriteInt F, C\TexDown
			WriteInt F, C\RndStartFrame
			WriteInt F, C\TexAnimSpeed
			WriteInt F, C\VShapeBased
			WriteFloat F, C\VelocityX#
			WriteFloat F, C\VelocityY#
			WriteFloat F, C\VelocityZ#
			WriteFloat F, C\VelocityRndX#
			WriteFloat F, C\VelocityRndY#
			WriteFloat F, C\VelocityRndZ#
			WriteFloat F, C\ForceX#
			WriteFloat F, C\ForceY#
			WriteFloat F, C\ForceZ#
			WriteFloat F, C\ScaleStart#
			WriteFloat F, C\ScaleChange#
			WriteInt F, C\Lifespan
			WriteFloat F, C\AlphaStart#
			WriteFloat F, C\AlphaChange#
			WriteInt F, C\BlendMode
			WriteInt F, C\Shape
			WriteFloat F, C\MinRadius#
			WriteFloat F, C\MaxRadius#
			WriteFloat F, C\Width#
			WriteFloat F, C\Height#
			WriteFloat F, C\Depth#
			WriteInt F, C\ShapeAxis
		CloseFile F
		Return True
	Else
		Return False
	EndIf

End Function

; Loads an emitter configuration from file
Function RP_LoadEmitterConfig(File$, Texture, FaceEntity)

	F = ReadFile(File$)
	If F = 0 Then Return False

		C.RP_EmitterConfig = New RP_EmitterConfig
		C\Texture = Texture
		C\FaceEntity = FaceEntity

		For i = Len(File$) To 1 Step -1
			If Mid$(File$, i, 1) = "."
				File$ = Left$(File$, i - 1)
			ElseIf Mid$(File$, i, 1) = "\" Or Mid$(File$, i, 1) = "/"
				File$ = Mid$(File$, i + 1)
				Exit
			EndIf
		Next
		C\Name$ = File$

		C\MaxParticles = ReadInt(F)
		C\ParticlesPerFrame = ReadInt(F)
		C\TexAcross = ReadInt(F)
		C\TexDown = ReadInt(F)
		C\RndStartFrame = ReadInt(F)
		C\TexAnimSpeed = ReadInt(F)
		C\VShapeBased = ReadInt(F)
		C\VelocityX# = ReadFloat#(F)
		C\VelocityY# = ReadFloat#(F)
		C\VelocityZ# = ReadFloat#(F)
		C\VelocityRndX# = ReadFloat#(F)
		C\VelocityRndY# = ReadFloat#(F)
		C\VelocityRndZ# = ReadFloat#(F)
		C\ForceX# = ReadFloat#(F)
		C\ForceY# = ReadFloat#(F)
		C\ForceZ# = ReadFloat#(F)
		C\ScaleStart# = ReadFloat#(F)
;		C\ScaleChange# = ReadFloat#(F)
		C\Lifespan = ReadInt(F)
		C\AlphaStart# = ReadFloat#(F)
		C\AlphaChange# = ReadFloat#(F)
		C\BlendMode = ReadInt(F)
		C\Shape = ReadInt(F)
		C\MinRadius# = ReadFloat#(F)
		C\MaxRadius# = ReadFloat#(F)
		C\Width# = ReadFloat#(F)
		C\Height# = ReadFloat#(F)
		C\Depth# = ReadFloat#(F)
		C\ShapeAxis = ReadInt(F)
	CloseFile F

	Return Handle(C)

End Function

; Creates a new emitter with a given configuration and returns the ID
Function RP_CreateEmitter(Configuration)

	; Find config
	C.RP_EmitterConfig = Object.RP_EmitterConfig(Configuration)
	If C = Null Then Return False

	; Create emitter
	E.RP_Emitter = New RP_Emitter
	E\Enabled = True
	E\Config = C
	E\EmitterEN = CreatePivot()
	NameEntity E\EmitterEN, Handle(E)
	E\MeshEN = CreateMesh()
	CreateSurface(E\MeshEN)
	EntityFX E\MeshEN, 1 + 2 + 32
	EntityTexture E\MeshEN, E\Config\Texture
	EntityBlend E\MeshEN, E\Config\BlendMode

	; Create initial particles
	For i = 1 To C\MaxParticles
		RP_CreateParticle(E)
	Next

	; Return entity
	Return E\EmitterEN

End Function

; Returns how many particles are current alive in an emitter
Function RP_EmitterActiveParticles(ID)

	E.RP_Emitter = Object.RP_Emitter(EntityName$(ID))
	If E <> Null
		Return E\ActiveParticles
	Else
		Return -1
	EndIf

End Function

; Enables an emitter
Function RP_EnableEmitter(ID)

	E.RP_Emitter = Object.RP_Emitter(EntityName$(ID))
	If E <> Null
		E\Enabled = True
		ShowEntity E\MeshEN
		Return True
	Else
		Return False
	EndIf

End Function

; Disables an emitter
Function RP_DisableEmitter(ID)

	E.RP_Emitter = Object.RP_Emitter(EntityName$(ID))
	If E <> Null
		E\Enabled = False
		Return True
	Else
		Return False
	EndIf

End Function

; Hides an emitter
Function RP_HideEmitter(ID)

	E.RP_Emitter = Object.RP_Emitter(EntityName$(ID))
	If E <> Null
		HideEntity E\MeshEN
		Return True
	Else
		Return False
	EndIf

End Function

; Shows an emitter
Function RP_ShowEmitter(ID)

	E.RP_Emitter = Object.RP_Emitter(EntityName$(ID))
	If E <> Null
		ShowEntity E\MeshEN
		Return True
	Else
		Return False
	EndIf

End Function

; Frees an emitter only after all existing particles have finished their lives
Function RP_KillEmitter(ID, FreeConfig = False, FreeTex = False)

	E.RP_Emitter = Object.RP_Emitter(EntityName$(ID))
	If E <> Null
		E\KillMode = 1
		If FreeConfig = True
			If FreeTex = True
				E\KillMode = 2
			Else
				E\KillMode = 3
			EndIf
		ElseIf FreeTex = True
			E\KillMode = 4
		EndIf
		Return True
	Else
		Return False
	EndIf

End Function

; Frees an emitter
Function RP_FreeEmitter(ID, FreeConfig = False, FreeTex = False)

	E.RP_Emitter = Object.RP_Emitter(EntityName$(ID))
	If E <> Null
		If FreeConfig = True
			RP_FreeEmitterConfig(Handle(E\Config), FreeTex)
		ElseIf FreeTex = True
			; Free texture, making sure that all instances of this texture in the particle system are set to 0
			For C.RP_EmitterConfig = Each RP_EmitterConfig
				If C <> E\Config And C\Texture = E\Config\Texture Then C\Texture = 0
			Next
			FreeTexture E\Config\Texture
			E\Config\Texture = 0
		EndIf
		For P.RP_Particle = Each RP_Particle
			If P\E = E Then Delete P
		Next
		FreeEntity E\MeshEN
		FreeEntity E\EmitterEN
		Delete E
		Return True
	Else
		Return False
	EndIf

End Function

; Frees every emitter, particle and configuration
Function RP_Clear(Configs = True, Textures = True)

	For E.RP_Emitter = Each RP_Emitter
		RP_FreeEmitter(E\EmitterEN, Configs, Textures)
	Next

End Function
;~IDEal Editor Parameters:
;~C#Blitz3D