; ParticleWorks - Particle System Library
; By Jocelyn 'GoSsE' Perreault, http://www.nuloen.com/
; Last Updated: November 12 2003
; Version: 1.0.1
; This source code is copyrighted (c) to Jocelyn Perreault.
; It is FreeWare. You have no limitiations in regard to 
; applications/games that you release/create with it, either
; they be freeware or commercial. However, you cannot release
; this source code under another name or release a similar
; library with it. It cannot be redistributed. The code may be 
; modified to be tweaked but you cannot distribute it. If you
; made a modification that you think the community would
; benifit, please let me know.
; If you make tons of money with a commercial project,
; a free copy wouldn't hurt ;)
; I would love to have an e-mail and a place in the credits
; of your projects, but it is up to you.
; Thanks,
;                 Jocelyn "GoSsE Korupted" Perreault
;                  gosse@nuloen.com
;                  Nuclear Loaded Entertainment
;                  http://www.nuloen.com/

; ======================================================================
; Different objects
; ======================================================================

; The emitters, which generates new Particles at each cycle
Type PW_Emitter
	Field pvtEmitter			; Its pivot for 3D control (serves as ID)
	Field objDummyParticle		; The Dummy Particle which is copied
	Field texParticle			; The Particles' Texture
	Field Velocity#				; The Velocity of the particles
	Field RotationX#			; The Rotation in X of the particles
	Field RotationY#			; The Rotation in Y of the particles
	Field RotationZ#			; The Rotation in Z of the particles
	Field Acceleration#			; The Acceleration of the particles (%)
	Field Gravity#				; The Gravity applied on the particles
	Field Life#					; The life of the Emitter (# of particles)
	Field ParticleLife			; The life of the particles (# of cycles)
	Field Flow#					; Number of particles it generates each cycle
	Field SpreadX#				; The spread in X at which it throws particles
	Field SpreadY#				; The spread in Y at which it throws particles
	Field Alpha#				; The initial alpha for particles
	Field ScaleX#				; The initial scale in X
	Field ScaleY#				; The initial scale in Y
	Field ScaleZ#				; The initial scale in Z
	Field FadeStart				; Particle Life remaining to start fading
	Field Growth#				; The growth rate of particles (%)
	Field RotationSpread#		; Rotation variation for particles
	Field ScaleSpread#			; Scale variation for particles
	Field AlphaSpread#			; Alpha variation for particles
	Field ParticleType			; Particle type for collisions
	Field ParticleRadius#		; Collision radius for particles
	Field InitialAngleX#		; Initial particle angle in X
	Field InitialAngleY#		; Initial particle angle in Y
	Field InitialAngleZ#		; Initial particle angle in Z
	Field AngleSpread#			; Initial angle spread
	Field Frames				; Texture animation frames count
	Field CyclesPerFrame		; Cycles per texture frame
	Field VelocitySpread#		; Spread of the particle's velocity
	Field FlowCount				; Number of frames it went for small flow
	Field Entity_FX				; Particle's entity FX
	Field objFace				; Object handle that the particles face
	Field TiedPivot				; Is the particle angle tied to its pivot
	Field isMesh				; Is the particle a mesh?
	Field pCount				; Particle count for that emitter
	Field flowTime				; Time for a new particle
	Field totalCycles			; Total Cycles for anim textures
	Field lifeSpread			; Life spread for particles
End Type

; ----------------------------------------------------------------------

; The Particles, which acts upon its emitter
Type PW_Particle
	Field EmitterID				; Its Emitter's ID
	Field Emitter.PW_Emitter	; The pointer to the Emitter type
	Field pvtParticle			; The pivot handling 3D control
	Field objParticle			; The copied mesh of the particle w/ rotation
	Field Rotation				; Is there rotation applied to the particle?
	Field GravSpeed#			; The current Gravity speed
	Field FirstFrame			; Timer of the first texture frame
	Field LastFrame				; ID of the last frame
	Field Velocity#				; The Velocity of the particle
	Field Life					; The life of the particle
	Field ScaleX#				; The scale in X
	Field ScaleY#				; The scale in Y
	Field ScaleZ#				; The scale in Z
	Field RotationX#			; The Rotation in X of the particle
	Field RotationY#			; The Rotation in Y of the particle
	Field RotationZ#			; The Rotation in Z of the particle
	Field Alpha#				; The alpha level for particle
End Type

; ----------------------------------------------------------------------

; The modifiers, which exerces force on particles from an emitter
Type PW_Modifier
	Field pvtModifier			; Its pivot for 3D control (serves as ID)
	Field emitterID				; The emitter it affects
	Field MinRange#				; The minimum range of effect
	Field MaxRange#				; The maximum range of effect
	Field MinForce#				; The minimum force applied (when at max range)
	Field MaxForce#				; The maximum force applied (when at min range)
	Field AffectAngle			; If it affects the particles angle
End Type

; ======================================================================
; Globals and Constants
; ======================================================================
Global PW_Emitters.PW_Emitter			; All the Emitters Collection
Global PW_Particles.PW_Particle			; All the Particles Collection
Global PW_Modifiers.PW_Modifier			; All the Modifiers Collection
Global PW_pvtModifierTemp = CreatePivot() 	; A temporary pivot for modifiers

; ======================================================================
; Functions
; ======================================================================

; CreateEmitter - To create a new Emitter
; Returns the pivot entity handle
Function PW_CreateEmitter()
	; Creates a New PW_Emitter
	PW_Emitters.PW_Emitter = New PW_Emitter
	; Assign the pivot and ID
	PW_Emitters\pvtEmitter = CreatePivot()
	; Sets Default values
	PW_Emitters\Alpha# = 1
	PW_SetScale(PW_Emitters\pvtEmitter, 1, 1, 1, 0, 1)
	PW_Emitters\PCount = 1
	; Returns the Pivot as the ID
	Return PW_Emitters\pvtEmitter
End Function

; ----------------------------------------------------------------------


; SetEmitter, sets the emitter parameters
Function PW_SetEmitter(ID, Life#, ParticleLife, LifeSpread, Flow#, SpreadX#, SpreadY#)
	; Goes through all emitters
	For PW_Emitters.PW_Emitter = Each PW_Emitter
		If PW_Emitters\pvtEmitter = ID Then
			Exit
		End If
	Next	
	PW_Emitters\Life# = Life#
	PW_Emitters\ParticleLife = ParticleLife
	PW_Emitters\LifeSpread = LifeSpread
	PW_Emitters\Flow# = Flow#
	PW_Emitters\SpreadX# = SpreadX#
	PW_Emitters\SpreadY# = SpreadY#
	PW_Emitters\FlowTime = Int(1 / Flow#)
End Function

; ----------------------------------------------------------------------

; SetParticle, sets the particles parameters
Function PW_SetParticle(ID, objPart, typPart, partRad#, objToFace, TiedToPivot)
	; Goes through all emitters
	For PW_Emitters.PW_Emitter = Each PW_Emitter
		If PW_Emitters\pvtEmitter = ID Then
			Exit
		End If
	Next	
	; Assign the dummy particle mesh
	PW_Emitters\objDummyParticle = objPart
	PW_Emitters\ParticleType = typPart
	PW_Emitters\ParticleRadius# = PartRad#
	PW_Emitters\objFace = objToFace
	PW_Emitters\TiedPivot = TiedToPivot
	PW_Emitters\isMesh = (EntityClass(objPart) = "Mesh")

End Function

; ----------------------------------------------------------------------

; SetTexture, sets texture and effects
Function PW_SetTexture(ID, texPart, Frames, CyclesPerFrame, EntFX)
	; Goes through all emitters
	For PW_Emitters.PW_Emitter = Each PW_Emitter
		If PW_Emitters\pvtEmitter = ID Then
			Exit
		End If
	Next	
	; Assign the texture
	PW_Emitters\texParticle = texPart
	PW_Emitters\Frames = Frames
	PW_Emitters\CyclesPerFrame = CyclesPerFrame
	PW_Emitters\Entity_FX = EntFX
	PW_Emitters\TotalCycles = Frames * CyclesPerFrame
End Function

; SetVelocity, sets the various velocities upon particles
Function PW_SetVelocity(ID, Vel#, VelSpread#, Acc#, Grav#)
	; Goes through all emitters
	For PW_Emitters.PW_Emitter = Each PW_Emitter
		If PW_Emitters\pvtEmitter = ID Then
			Exit
		End If
	Next	
	PW_Emitters\Velocity# = Vel#
	PW_Emitters\VelocitySpread# = VelSpread#
	PW_Emitters\Acceleration# = Acc#
	PW_Emitters\Gravity# = Grav#

End Function

; ----------------------------------------------------------------------

; SetAngle, sets the initial angle and spread
Function PW_SetAngle(ID, IAX#, IAY#, IAZ#, AngleSpread#)
	; Goes through all emitters
	For PW_Emitters.PW_Emitter = Each PW_Emitter
		If PW_Emitters\pvtEmitter = ID Then
			Exit
		End If
	Next	
	PW_Emitters\InitialAngleX# = IAX#
	PW_Emitters\InitialAngleY# = IAY#
	PW_Emitters\InitialAngleZ# = IAZ#
	PW_Emitters\AngleSpread# = AngleSpread#
End Function


; ----------------------------------------------------------------------

; SetRotation, to sets the particle rotation
Function PW_SetRotation(ID, RotX#, RotY#, RotZ#, RotSpread#)
	; Goes through all emitters
	For PW_Emitters.PW_Emitter = Each PW_Emitter
		If PW_Emitters\pvtEmitter = ID Then
			Exit
		End If
	Next	
	PW_Emitters\RotationX# = RotX#
	PW_Emitters\RotationY# = RotY#
	PW_Emitters\RotationZ# = RotZ#
	PW_Emitters\RotationSpread# = RotSpread#

End Function

; ----------------------------------------------------------------------

; SetAlpha, sets the particles alpha settings
Function PW_SetAlpha(ID, Alpha#, AlphaSpread#, FadeStart)
	; Goes through all emitters
	For PW_Emitters.PW_Emitter = Each PW_Emitter
		If PW_Emitters\pvtEmitter = ID Then
			Exit
		End If
	Next	
	PW_Emitters\Alpha# = Alpha#
	PW_Emitters\AlphaSpread# = AlphaSpread#
	PW_Emitters\FadeStart = FadeStart

End Function

; ----------------------------------------------------------------------

; SetScale, sets the scale settings and growth
Function PW_SetScale(ID, ScX#, ScY#, ScZ#, ScSpread#, G#)
	; Goes through all emitters
	For PW_Emitters.PW_Emitter = Each PW_Emitter
		If PW_Emitters\pvtEmitter = ID Then
			Exit
		End If
	Next	
	PW_Emitters\ScaleX# = ScX#
	PW_Emitters\ScaleY# = ScY#
	PW_Emitters\ScaleZ# = ScZ#
	PW_Emitters\ScaleSpread# = ScSpread#
	PW_Emitters\Growth# = G#
End Function

; ----------------------------------------------------------------------

; UpdateEmitters - Goes through emitters and generates New PW_Particles
Function PW_UpdateEmitters()
	; Goes through all emitters
	For PW_Emitters.PW_Emitter = Each PW_Emitter
		; Update if the emitter is still alive
		If PW_Emitters\pvtEmitter <> 0 Then
			If PW_Emitters\Flow# >= 1 Then
				; Cycle through the Flow and generates New PW_Particles
				For newParts = 1 To PW_Emitters\Flow#
					PW_GenerateParticle()
				Next
			Else
				; Checks if its time for a New PW_Particle
				If PW_Emitters\FlowCount = PW_Emitters\FlowTime Then
					PW_GenerateParticle()
					PW_Emitters\FlowCount = 0
				Else
					PW_Emitters\FlowCount = PW_Emitters\FlowCount + 1
				End If
			End If
		End If
		; Checks if its an infinite emitter
		If PW_Emitters\Life# > 0 Then
			; Removes life from the Emitter and delete it if its depleted
			PW_Emitters\Life# = PW_Emitters\Life# - PW_Emitters\Flow#
			If PW_Emitters\Life# <= 0 Then
				FreeEntity PW_Emitters\pvtEmitter
				PW_Emitters\pvtEmitter = 0
			End If
			If PW_Emitters\PCount = 0 And PW_Emitters\pvtEmitter = 0 Then
				Delete PW_Emitters
			End If
		End If
		If PW_Emitters <> Null Then
			; Resets the PCount for the next counting
			PW_Emitters\PCount = 0
		End If
	Next
End Function

; ----------------------------------------------------------------------

; UpdateParticles - Updates the particles
Function PW_UpdateParticles()
	Local TotalP
	TotalP = 0
	; Goes through all particles
	For PW_Particles.PW_Particle = Each PW_Particle
		; Decrease life
		TotalP = TotalP + 1
		PW_Particles\Emitter\PCount = PW_Particles\Emitter\PCount + 1
		PW_Particles\Life = PW_Particles\Life - 1
		; Delete particle if life is <= 0
		If PW_Particles\Life <= 0 Then
			FreeEntity PW_Particles\pvtParticle
			FreeEntity PW_Particles\objParticle
			Delete PW_Particles
		Else
			; Applies the velocity
			MoveEntity PW_Particles\pvtParticle, 0, 0, PW_Particles\Velocity#
			; Applies the gravity
			If PW_Particles\Emitter\Gravity# <> 0 Then
				PW_Particles\GravSpeed# = PW_Particles\GravSpeed# + PW_Particles\Emitter\Gravity#
				TranslateEntity PW_Particles\pvtParticle, 0, PW_Particles\GravSpeed#, 0
			End If
			; Applies the rotation
			If PW_Particles\Rotation Then
				TurnEntity PW_Particles\objParticle, PW_Particles\Emitter\RotationX#, PW_Particles\Emitter\RotationY#, PW_Particles\Emitter\RotationZ#
			End If
			; If the particle is tied, align with pivot
			If PW_Particles\Emitter\TiedPivot Then
				RotateEntity PW_Particles\objParticle, EntityPitch(PW_Particles\pvtParticle), EntityYaw(PW_Particles\pvtParticle), EntityRoll(PW_Particles\pvtParticle)
			End If
			; Applies the acceleration on velocity
			If PW_Particles\Emitter\Acceleration# <> 1 Then
				PW_Particles\Velocity# = PW_Particles\Velocity# * PW_Particles\Emitter\Acceleration#
			End If
			; Face toward the face objects if avail.
			If PW_Particles\Emitter\objFace > 0 Then
				PointEntity PW_Particles\objParticle, PW_Particles\Emitter\objFace
			End If		
			; Performs certain operations only if the particle is a mesh
			If PW_Particles\Emitter\isMesh Then
				; Fade particle out
				If PW_Particles\Life < PW_Particles\Emitter\FadeStart Then
					EntityAlpha PW_Particles\objParticle, PW_Particles\Alpha# * (1 - Float(PW_Particles\Emitter\FadeStart - PW_Particles\Life) / Float(PW_Particles\Emitter\FadeStart))
				End If
				; Applies the growth
				If PW_Particles\Emitter\Growth# <> 1 Then
					PW_Particles\ScaleX# = PW_Particles\ScaleX# * PW_Particles\Emitter\Growth#
					PW_Particles\ScaleY# = PW_Particles\ScaleX# * PW_Particles\Emitter\Growth#
					PW_Particles\ScaleZ# = PW_Particles\ScaleX# * PW_Particles\Emitter\Growth#
					ScaleEntity PW_Particles\objParticle, PW_Particles\ScaleX#, PW_Particles\ScaleY#, PW_Particles\ScaleZ#
				End If
				; Applies the texture changes
				If PW_Particles\Emitter\Frames > 0 Then
					If PW_Particles\Emitter\CyclesPerFrame > 0 Then
						Frame = ((MilliSecs() - PW_Particles\FirstFrame) Mod PW_Particles\Emitter\TotalCycles) / PW_Particles\Emitter\CyclesPerFrame
						While Frame < 0
							Frame = Frame + PW_Particles\Emitter\Frames
						Wend
						If Frame <> PW_Particles\LastFrame Then
							EntityTexture PW_Particles\objParticle, PW_Particles\Emitter\texParticle, Frame
							PW_Particles\LastFrame = Frame
						End If
					End If
				End If
			End If
			; Performs modifiers operations if one or more is assigned
			For PW_Modifiers.PW_Modifier = Each PW_Modifier
				If PW_Modifiers\emitterID = PW_Particles\emitterID Then
					range# = EntityDistance(PW_Particles\objParticle, PW_Modifiers\pvtModifier)
					; Checks if the particle is within area of effect of modifier
					If range# >= PW_Modifiers\MinRange# And range# <= PW_Modifiers\MaxRange# Then
						; Calculate the pull force on the particle
						force# = (range# - PW_Modifiers\MinRange#) / (PW_Modifiers\MaxRange# - PW_Modifiers\MinRange#)
						force# = force# * (PW_Modifiers\MaxForce# - PW_Modifiers\MinForce#) + PW_Modifiers\MinForce#
						; Pull on the particle with the aid of the temp pivot
						PW_ReachPosition(PW_pvtModifierTemp, PW_Particles\pvtParticle, 1)
						PointEntity PW_pvtModifierTemp, PW_Modifiers\pvtModifier
						MoveEntity PW_pvtModifierTemp, 0, 0, force#
						PositionEntity PW_Particles\pvtParticle, EntityX(PW_pvtModifierTemp), EntityY(PW_pvtModifierTemp), EntityZ(PW_pvtModifierTemp)
						; Affects the angle if it must
						If PW_Modifiers\AffectAngle Then
							PW_ReachAngle(PW_Particles\pvtParticle, PW_pvtModifierTemp, 0.1)
						End If
					End If
				End If
			Next
			; Assign the pivot's position to the particle
			PositionEntity PW_Particles\objParticle, EntityX(PW_Particles\pvtParticle), EntityY(PW_Particles\pvtParticle), EntityZ(PW_Particles\pvtParticle)
		End If
	Next
	Return TotalP
End Function

; ----------------------------------------------------------------------

; CreateModifier - To create a New PW_Modifier
Function PW_CreateModifier(emitterID, MinRange#, MaxRange#, MinForce#, MaxForce#, AffectAngle)
	; Creates a New PW_Modifier
	PW_Modifiers.PW_Modifier = New PW_Modifier 
	; Assign the pivot and ID
	PW_Modifiers\pvtModifier = CreatePivot()
	; Assign its properties
	PW_Modifiers\emitterID = emitterID
	PW_Modifiers\MinRange#	= MinRange#
	PW_Modifiers\MaxRange#	= MaxRange#
	PW_Modifiers\MinForce#	= MinForce#
	PW_Modifiers\MaxForce#	= MaxForce#
	PW_Modifiers\AffectAngle = AffectAngle
	; Returns the Pivot as the ID
	Return PW_Modifiers\pvtModifier 
	
End Function
	
; ----------------------------------------------------------------------

; DuplicateModifier - To duplicate a modifier but with for a different emitter
Function PW_DuplicateModifier(ID, newEmitterID, ParentIt)
	; Save the source modifier's properties
	For PW_Modifiers.PW_Modifier = Each PW_Modifier
		If PW_Modifiers\pvtModifier = ID Then
			MinRange# = PW_Modifiers\MinRange#
			MaxRange# = PW_Modifiers\MaxRange#
			MinForce# = PW_Modifiers\MinForce#
			MaxForce# = PW_Modifiers\MaxForce#
			AffectAngle  = PW_Modifiers\AffectAngle
		End If
	Next
	; Creates a New PW_Modifier
	PW_Modifiers.PW_Modifier = New PW_Modifier 
	; Assign the pivot and ID, and parent it if it must
	If ParentIt Then
		PW_Modifiers\pvtModifier = CreatePivot(ID)
	Else
		PW_Modifiers\pvtModifier = CreatePivot()
	End If
	; Assign its properties
	PW_Modifiers\emitterID = newEmitterID
	PW_Modifiers\MinRange#	= MinRange#
	PW_Modifiers\MaxRange#	= MaxRange#
	PW_Modifiers\MinForce#	= MinForce#
	PW_Modifiers\MaxForce#	= MaxForce#
	PW_Modifiers\AffectAngle = AffectAngle 
	; Returns the Pivot as the ID
	Return PW_Modifiers\pvtModifier 

End Function

; ----------------------------------------------------------------------

; ModifyModifier - To changes certain parameters of a modifier
Function PW_ModifyModifier(ID, emitterID, MinRange#, MaxRange#, MinForce#, MaxForce#, AffectAngle)
	; Goes through all modifiers
	For PW_Modifiers.PW_Modifier = Each PW_Modifier
		If PW_Modifiers\pvtModifier = ID Then
			; Assign its properties
			PW_Modifiers\emitterID = emitterID
			PW_Modifiers\MinRange#	= MinRange#
			PW_Modifiers\MaxRange#	= MaxRange#
			PW_Modifiers\MinForce#	= MinForce#
			PW_Modifiers\MaxForce#	= MaxForce#
			PW_Modifiers\AffectAngle = AffectAngle
		End If
	Next	
End Function

; ----------------------------------------------------------------------

; EmitterExists - To check if an emitter still exists
Function PW_EmitterExists(ID)
	; Goes through all emitters
	For PW_Emitters.PW_Emitter = Each PW_Emitter
		If PW_Emitters\pvtEmitter = ID Then
			If PW_Emitters\pvtEmitter <> 0 Then
				Return True
			End If
		End If
	Next
	Return False
End Function

; ----------------------------------------------------------------------

; ModifierExists - To check if a modifier still exists
Function PW_ModifierExists(ID)
	; Goes through all modifiers
	For PW_Modifiers.PW_Modifier = Each PW_Modifier
		If PW_Modifiers\pvtModifier = ID Then
			Return True
		End If
	Next
	Return False
End Function

; ----------------------------------------------------------------------

; KillParticles - Kills the particles from an emitter
Function PW_KillParticles(emitterID)
	; Goes through all particles
	For PW_Particles.PW_Particle = Each PW_Particle
		If PW_Particles\EmitterID = emitterID Then
			FreeEntity PW_Particles\pvtParticle
			FreeEntity PW_Particles\objParticle
			Delete PW_Particles
		End If
	Next
End Function

; ----------------------------------------------------------------------

; KillEmitter - Kills a specified emitter prematurely
Function PW_KillEmitter(ID)
	; Goes through all emitters
	For PW_Emitters.PW_Emitter = Each PW_Emitter
		If PW_Emitters\pvtEmitter = ID Then
			FreeEntity PW_Emitters\pvtEmitter
			PW_Emitters\pvtEmitter = 0
		End If
	Next
End Function

; ----------------------------------------------------------------------

; KillModifier - Kills a specified modifier prematurely
Function PW_KillModifier(ID)
	; Goes through all modifiers
	For PW_Modifiers.PW_Modifier = Each PW_Modifier
		If PW_Modifiers\pvtModifier = ID Then
			FreeEntity PW_Modifiers\pvtModifier
			Delete PW_Modifiers
		End If
	Next
End Function

; ======================================================================
; Misc. Functions that are not part of the lib but have to be included
; ======================================================================

; GenerateParticle - Creates a New PW_Particle
Function PW_GenerateParticle()
	; Creates the particle
	PW_Particles.PW_Particle = New PW_Particle
	; Assign the ID
	PW_Particles\EmitterID	= PW_Emitters\pvtEmitter
	PW_Particles\Emitter = PW_Emitters
	; Create a pivot
	PW_Particles\pvtParticle = CreatePivot()
	; Copy the particle dummy mesh or particle
	If PW_Emitters\isMesh Then
		PW_Particles\objParticle = CopyMesh(PW_Emitters\objDummyParticle)
	Else
		PW_Particles\objParticle = CopyEntity(PW_Emitters\objDummyParticle)
	End If
	; Texture And other visual effects
	PW_Particles\LastFrame = Rand(0, PW_Emitters\Frames - 1)
	PW_Particles\FirstFrame = MilliSecs() + Rand(0, PW_Particles\LastFrame * PW_Emitters\CyclesPerFrame)
	PW_Particles\Alpha# = PW_Emitters\Alpha# + Rnd(0, 2 * PW_Emitters\AlphaSpread#) - PW_Emitters\AlphaSpread#
	PW_Particles\ScaleX# = PW_Emitters\ScaleX# + Rnd(0, 2 * PW_Emitters\ScaleSpread#) - PW_Emitters\ScaleSpread#
	PW_Particles\ScaleY# = PW_Emitters\ScaleY# + Rnd(0, 2 * PW_Emitters\ScaleSpread#) - PW_Emitters\ScaleSpread#
	PW_Particles\ScaleZ# = PW_Emitters\ScaleZ# + Rnd(0, 2 * PW_Emitters\ScaleSpread#) - PW_Emitters\ScaleSpread#
	If PW_Particles\Emitter\isMesh Then
		; Performs certain operations only if the particle is a mesh
		If PW_Emitters\texParticle <> 0 Then
			EntityTexture PW_Particles\objParticle, PW_Emitters\texParticle
		End If
		EntityAlpha PW_Particles\objParticle, PW_Particles\Alpha#
		ScaleEntity PW_Particles\objParticle, PW_Particles\ScaleX#, PW_Particles\ScaleY#, PW_Particles\ScaleZ#
		EntityFX PW_Particles\objParticle, PW_Emitters\Entity_FX
	End If
	; Inherit the properties
	PW_Particles\Velocity# = PW_Emitters\Velocity# + Rnd(0, 2 * PW_Emitters\VelocitySpread#) - PW_Emitters\VelocitySpread#
	PW_Particles\RotationX# = PW_Emitters\RotationX# + Rnd(0, 2 * PW_Emitters\RotationSpread#) - PW_Emitters\RotationSpread#
	PW_Particles\RotationY# = PW_Emitters\RotationY# + Rnd(0, 2 * PW_Emitters\RotationSpread#) - PW_Emitters\RotationSpread#
	PW_Particles\RotationZ# = PW_Emitters\RotationZ# + Rnd(0, 2 * PW_Emitters\RotationSpread#) - PW_Emitters\RotationSpread#
	PW_Particles\Life = PW_Emitters\ParticleLife + (Rand(0, 2) - 1) * PW_Emitters\LifeSpread
	; Angles and Rotation
	If PW_Particles\RotationX# = 0 And PW_Particles\RotationY# = 0 And PW_Particles\RotationZ# = 0 Then
		PW_Particles\Rotation = False
	Else
		PW_Particles\Rotation = True
	End If
	RotateEntity PW_Particles\pvtParticle, EntityPitch(PW_Emitters\pvtEmitter) - PW_Emitters\SpreadX# + Rnd(0, PW_Emitters\SpreadX# * 2), EntityYaw(PW_Emitters\pvtEmitter) - PW_Emitters\SpreadY# + Rnd(0, PW_Emitters\SpreadY# * 2), EntityRoll(PW_Emitters\pvtEmitter)
	If PW_Particles\Emitter\TiedPivot Then
		RotateEntity PW_Particles\objParticle, EntityPitch(PW_Particles\pvtParticle), EntityYaw(PW_Particles\pvtParticle), EntityRoll(PW_Particles\pvtParticle)
		Rotation = False
	Else
		RotateEntity PW_Particles\objParticle, PW_Emitters\InitialAngleX# + Rnd(0, 2 * PW_Emitters\AngleSpread#) - PW_Emitters\AngleSpread#, PW_Emitters\InitialAngleY# + Rnd(0, 2 * PW_Emitters\AngleSpread#) - PW_Emitters\AngleSpread#, PW_Emitters\InitialAngleZ# + Rnd(0, 2 * PW_Emitters\AngleSpread#) - PW_Emitters\AngleSpread#
	End If
	; Position the particle
	PositionEntity PW_Particles\pvtParticle, EntityX(PW_Emitters\pvtEmitter), EntityY(PW_Emitters\pvtEmitter), EntityZ(PW_Emitters\pvtEmitter)
	PositionEntity PW_Particles\objParticle, EntityX(PW_Emitters\pvtEmitter), EntityY(PW_Emitters\pvtEmitter), EntityZ(PW_Emitters\pvtEmitter)
	; Collisions Properties
	If PW_Emitters\ParticleType <> 0 Then
		EntityType PW_Particles\pvtParticle, PW_Emitters\ParticleType
		EntityRadius PW_Particles\pvtParticle, PW_Emitters\ParticleRadius			
	End If
End Function

Function PW_ReachPosition(obj1, obj2, amount#)
	x1# = EntityX(obj1, True)
	y1# = EntityY(obj1, True)
	z1# = EntityZ(obj1, True)
	x2# = EntityX(obj2, True)
	y2# = EntityY(obj2, True)
	z2# = EntityZ(obj2, True)
	dispx# = (x2# - x1#) * amount#
	dispy# = (y2# - y1#) * amount#
	dispz# = (z2# - z1#) * amount#
	TranslateEntity obj1, dispx#, dispy#, dispz#, True
End Function

Function PW_ReachAngle(obj1, obj2, amount#)
	x1# = EntityPitch(obj1, True)
	y1# = EntityYaw(obj1, True)
	z1# = EntityRoll(obj1, True)
	x2# = EntityPitch(obj2, True)
	y2# = EntityYaw(obj2, True)
	z2# = EntityRoll(obj2, True)
	dispx# = (x2# - x1#) * amount#
	dispy# = (y2# - y1#) * amount#
	dispz# = (z2# - z1#) * amount#
	RotateEntity obj1, PW_Wrap360#(dispx# + x1#), PW_Wrap360#(dispy# + y1#), PW_Wrap360#(dispz# + z1#), True
End Function

Function PW_Wrap360#(angle#)
	While angle < 0
		angle = angle + 360
	Wend
	While Not(angle < 360)
		angle = angle - 360
	Wend
	Return angle
End Function