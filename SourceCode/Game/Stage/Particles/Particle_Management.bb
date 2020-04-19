Function Player_ParticleManagement(p.tPlayer, d.tDeltaTime)
	
	; --- PARTICLE EFFECTS ---
	
	
;	For c.tCamera = Each tCamera
	
;		If (Game\Stage\Properties\Rain = 1) Then
;			SetTemplateEmitterBlend(Game\Stage\Rain, 1)
;			SetTemplateInterval(Game\Stage\Rain, 2/d\delta)
;			SetTemplateParticlesPerInterval(Game\Stage\Rain, 15)
;			SetTemplateEmitterLifeTime(Game\Stage\Rain, -1)
;			SetTemplateParticleLifeTime(Game\Stage\Rain, 2/d\delta, 2/d\delta)
;			SetTemplateTexture(Game\Stage\Rain, "Textures\Rain.png", 3)
;			SetTemplateOffset(Game\Stage\Rain, -120, 120, 40, 40, -120, 120)
;			SetTemplateVelocity(Game\Stage\Rain, 0, 0, -4.23*Game\Deltatime\Delta#, -4.03*Game\Deltatime\Delta#, 0, 0)
;			SetTemplateSize(Game\Stage\Rain, .9, .9)
;			SetTemplateAlphaVel(Game\Stage\Rain, True)
;			SetTemplateMaxParticles(Game\Stage\Rain,35)
;			
;		;	SetTemplateAlpha(Game\Stage\Rain, 1)
;			
;		EndIf
	
;		If (Game\Stage\Properties\Snow = 1) Then
	
	
;			SetTemplateParticleLifeTime(Game\Stage\Snow, 7/d\delta, 9/d\delta)
;		;	SetTemplateAlpha(Game\Stage\Snow, 1)
;			SetTemplateEmitterBlend(Game\Stage\Snow, 1)
;			SetTemplateInterval(Game\Stage\Snow, 2/d\delta)
;			SetTemplateParticlesPerInterval(Game\Stage\Snow, 1)
;			SetTemplateEmitterLifeTime(Game\Stage\Snow, -1)
;			SetTemplateTexture(Game\Stage\Snow, "Textures\Snow.png", 3)
;			SetTemplateOffset(Game\Stage\Snow, -120, 120, 40, 40, -120, 120)
;			SetTemplateVelocity(Game\Stage\Snow, .1, .1, -1.83*Game\Deltatime\Delta#, -1.83*Game\Deltatime\Delta#, 0, 0)
;				;SetTemplateGravity(Snow, .3)
;			SetTemplateSize(Game\Stage\Snow, .9, .9)
;			SetTemplateAlphaVel(Game\Stage\Snow, True)
;				;SetTemplateAlpha(Snow, 1)
;				;SetTemplateFixAngles(Rain, 0, 1)
;			SetTemplateMaxParticles(Game\Stage\Snow,40)
;			
;		EndIf
	
;	Next
			; -----------------------------
	
	; SKIDDING
	SetTemplateEmitterBlend(p\Objects\Template, 1)
	SetTemplateOffset(p\Objects\Template, -.1, .1, 0, 0, -.1, .1)
	;SetTemplateVelocity(p\Objects\Template, 0*d\delta, 0*d\delta, .05*d\Delta, .05*d\delta, 2*d\Delta, 2*d\Delta, True)
	SetTemplateVelocity(p\Objects\Template, -25, 25, 0, .3, -.37, -.4, True)
	SetTemplateAlphaVel(p\Objects\Template, True)
	SetTemplateSize(p\Objects\Template, 0.05, 0.1, 1.0, 1.5)
	SetTemplateSizeVel(p\Objects\Template, 1.07, 1, 1) ;.01, 1.01, 0.6
	SetTemplateRotation(p\Objects\Template, -8, 8)
	SetTemplateGravity(p\Objects\Template, -0.0009)
	SetTemplateInterval(p\Objects\Template, 8)
	SetTemplateParticleLifeTime(p\Objects\Template, 100*d\delta#, 120*d\delta#)
	
	SetTemplateEmitterLifeTime(p\Objects\Template, 14*d\delta#)
	
	; ROCKS
	SetTemplateEmitterBlend(p\Particles\Rock, 1)
	SetTemplateOffset(p\Particles\Rock, -.1, .1, 1, 1, -.1, .1)
	;SetTemplateVelocity(p\Objects\Template, 0*d\delta, 0*d\delta, .05*d\Delta, .05*d\delta, 2*d\Delta, 2*d\Delta, True)
	SetTemplateVelocity(p\Particles\Rock, -25, 25, 0.38, 0.45, -0.27, -0.35, True)
	SetTemplateAlphaVel(p\Particles\Rock, True)
	SetTemplateSize(p\Particles\Rock, 0.08, 0.09, 1.0, 1)
	SetTemplateSizeVel(p\Particles\Rock, 1, 1, 1) ;.01, 1.01, 0.6
	SetTemplateRotation(p\Particles\Rock, -8, 8)
	SetTemplateGravity(p\Particles\Rock, 0.06)
	SetTemplateInterval(p\Particles\Rock, 12)
	SetTemplateFloor(p\Particles\Rock, EntityY#(p\Objects\Entity)-1.2, 0.9)
	SetTemplateParticleLifeTime(p\Particles\Rock, 80*d\delta#, 90*d\delta#)
	
	SetTemplateEmitterLifeTime(p\Particles\Rock, 14*d\delta#)
	
	; LAND
	SetTemplateEmitterBlend(p\Particles\Land, 1)
	SetTemplateOffset(p\Particles\Land, -.1, .1, -0.5, -0.5, -.1, .1)
	SetTemplateVelocity(p\Particles\Land, -.08*d\delta, .12*d\delta, .01*d\Delta, .015*d\delta, -.08*d\delta, .12*d\delta)
	SetTemplateAlphaVel(p\Particles\Land, True)
	SetTemplateSize(p\Particles\Land, 0.1, 0.16, 1.0, 1.3)
	SetTemplateSizeVel(p\Particles\Land, .01, 1.2, 0.6) ;.01, 1.01, 0.6
	SetTemplateRotation(p\Particles\Land, -8, 8)
	SetTemplateGravity(p\Particles\Land, -0.0002)
	SetTemplateInterval(p\Particles\Land, 1)
	SetTemplateParticleLifeTime(p\Particles\Land, 40*d\delta#, 50*d\delta#)
	
	SetTemplateEmitterLifeTime(p\Particles\Land, 5*d\delta#)
	
	; STEP 1
	SetTemplateEmitterBlend(p\Particles\Step1, 1)
	SetTemplateOffset(p\Particles\Step1, -.1, .1, 0.7, 0.7, -.1, .1)
	SetTemplateVelocity(p\Particles\Step1, -.08*d\delta, .12*d\delta, .01*d\Delta, .015*d\delta, -.08*d\delta, .12*d\delta)
	SetTemplateAlphaVel(p\Particles\Step1, True)
	SetTemplateSize(p\Particles\Step1, 0.2, 0.2, 1.0, 1.3)
	SetTemplateSizeVel(p\Particles\Step1, 1.2, 1, 1) ;.01, 1.01, 0.6
	SetTemplateRotation(p\Particles\Step1, -8, 8)
	SetTemplateGravity(p\Particles\Step1, -0.0004)
	SetTemplateInterval(p\Particles\Step1, 1)
	SetTemplateParticleLifeTime(p\Particles\Step1, 40*d\delta#, 50*d\delta#)
	
	SetTemplateEmitterLifeTime(p\Particles\Step1, 1*d\delta#)
	
	; STEP 2
	SetTemplateEmitterBlend(p\Particles\Step2, 1)
	SetTemplateOffset(p\Particles\Step2, -.1, .1, 0.7, 0.7, -.1, .1)
	SetTemplateVelocity(p\Particles\Step2, -.08*d\delta, .12*d\delta, .01*d\Delta, .015*d\delta, -.08*d\delta, .12*d\delta)
	SetTemplateAlphaVel(p\Particles\Step2, True)
	SetTemplateSize(p\Particles\Step2, 0.2, 0.2, 1.6, 1.8)
	SetTemplateSizeVel(p\Particles\Step2, 1.5, 1, 1) ;.01, 1.01, 0.6
	SetTemplateRotation(p\Particles\Step2, -8, 8)
	SetTemplateGravity(p\Particles\Step2, -0.0013)
	SetTemplateInterval(p\Particles\Step2, 1)
	SetTemplateParticleLifeTime(p\Particles\Step2, 70*d\delta#, 80*d\delta#)
	
	SetTemplateEmitterLifeTime(p\Particles\Step2, 1*d\delta#)
	
	; EXPLODE
;	SetTemplateEmitterBlend(Game\Others\Explode, 1)
;	SetTemplateInterval(Game\Others\Explode, 2)
;	SetTemplateParticlesPerInterval(Game\Others\Explode, 15)
;	SetTemplateEmitterLifeTime(Game\Others\Explode, 220)
;	SetTemplateParticleLifeTime(Game\Others\Explode, 220, 220)
;	SetTemplateAnimTexture(Game\Others\Explode, "Textures\Explode.png", 3, 1, 64, 64, 39)
;	SetTemplateOffset(Game\Others\Explode, 0, 0, -1, -1, 0, 0)
;	SetTemplateVelocity(Game\Others\Explode, 0, 0, -4.23*Game\Deltatime\Delta#, -4.03*Game\Deltatime\Delta#, 0, 0)
;	SetTemplateSize(Game\Others\Explode, .9, .9)
;	SetTemplateAlphaVel(Game\Others\Explode, True)
;	SetTemplateMaxParticles(Game\Others\Explode,2)
	
		;	SetTemplateAlpha(Game\Stage\Rain, 1)
	
;	If ((GlobalTimer Mod 20) > 13.2) Then SetEmitter(p\Objects\TempPiv, p\Objects\Template)
	
;	Select p\Animation\Animation
;		Case 0
;			SetTemplateSize(p\Objects\Template, 0, 0, 0, 0)
;			SetTemplateSizeVel(p\Objects\Template, 0, 0)
	
	
	
;		Case 1
;			
;			SetTemplateSize(p\Objects\Template, 0, 0, 0, 0)
;			SetTemplateSizeVel(p\Objects\Template, 0, 0)
	
;			For e.Emitter = Each Emitter
;				If e\owner = ent Then
;			For r.Particle = Each Particle
;				If r\emitter = e Then Delete p
;			Next
;		EndIf
;	Next
	
;Case 2
;	SetTemplateSize(p\Objects\Template, 0.7, 0.7, 0.7, 0.7)
;	SetTemplateSizeVel(p\Objects\Template, 0.3, 1.0)
;	SetTemplateParticleLifeTime(p\Objects\Template, 2/d\delta, 2/d\delta)
	
;Case 3
;	SetTemplateSize(p\Objects\Template, 0, 0, 0, 0)
;	SetTemplateSizeVel(p\Objects\Template, 0, 0)
;	
;Case 4
;	SetTemplateSize(p\Objects\Template, 0, 0, 0, 0)
;	SetTemplateSizeVel(p\Objects\Template, 0, 0)
	
;Case 5
;	SetTemplateSize(p\Objects\Template, 0, 0, 0, 0)
;	SetTemplateSizeVel(p\Objects\Template, 0, 0)
	
;Case 6
;	SetTemplateSize(p\Objects\Template, 0, 0, 0, 0)
;	SetTemplateSizeVel(p\Objects\Template, 0, 0)
	
;Case 7
;	SetTemplateSize(p\Objects\Template, 0, 0, 0, 0)
;	SetTemplateSizeVel(p\Objects\Template, 0, 0)
	
;Case 8
;	SetTemplateSize(p\Objects\Template, 0.6, 0.6, 0.6, 0.6)
;	SetTemplateSizeVel(p\Objects\Template, 0.3, 1.0)
;	SetTemplateParticleLifeTime(p\Objects\Template, 2/d\delta, 2/d\delta)
	
;Case 9
;	SetTemplateSize(p\Objects\Template, 0, 0, 0, 0)
;	SetTemplateSizeVel(p\Objects\Template, 0, 0)
	
;Case 10
;	SetTemplateSize(p\Objects\Template, 0, 0, 0, 0)
;	SetTemplateSizeVel(p\Objects\Template, 0, 0)
	
;Case 11
;	SetTemplateSize(p\Objects\Template, 0, 0, 0, 0)
;	SetTemplateSizeVel(p\Objects\Template, 0, 0)
	
;Case 12
;	SetTemplateSize(p\Objects\Template, 0, 0, 0, 0)
;	SetTemplateSizeVel(p\Objects\Template, 0, 0)
	
;Case 13
;	SetTemplateSize(p\Objects\Template, 0.5, 0.5, 0.5, 0.5)
;	SetTemplateSizeVel(p\Objects\Template, 0.26, 1.0)
;	SetTemplateParticleLifeTime(p\Objects\Template, 2/d\delta, 2/d\delta)
	
;Case 14
;	SetTemplateSize(p\Objects\Template, 0, 0, 0, 0)
;	SetTemplateSizeVel(p\Objects\Template, 0, 0)
	
;Case 15
;	SetTemplateSize(p\Objects\Template, 0, 0, 0, 0)
;	SetTemplateSizeVel(p\Objects\Template, 0, 0)
;Case 16
;	SetTemplateSize(p\Objects\Template, 0, 0, 0, 0)
;	SetTemplateSizeVel(p\Objects\Template, 0, 0)
;Case 17
;	SetTemplateSize(p\Objects\Template, 0, 0, 0, 0)
;	SetTemplateSizeVel(p\Objects\Template, 0, 0)
;Case 18
;	SetTemplateSize(p\Objects\Template, 0, 0, 0, 0)
;	SetTemplateSizeVel(p\Objects\Template, 0, 0)
	
;Case 19
;	SetTemplateSize(p\Objects\Template, 0.7, 0.7, 0.7, 0.7)
;	SetTemplateSizeVel(p\Objects\Template, 0.3, 1.0)
;	SetTemplateParticleLifeTime(p\Objects\Template, 2/d\delta, 2/d\delta)
	
;Case 20
;	SetTemplateSize(p\Objects\Template, 0, 0, 0, 0)
;	SetTemplateSizeVel(p\Objects\Template, 0, 0)
	
;Case 21
;	SetTemplateSize(p\Objects\Template, 0, 0, 0, 0)
;	SetTemplateSizeVel(p\Objects\Template, 0, 0)
	
;Case 22
;	SetTemplateSize(p\Objects\Template, 0, 0, 0, 0)
;	SetTemplateSizeVel(p\Objects\Template, 0, 0)
	
;Case 23
;	SetTemplateSize(p\Objects\Template, 0, 0, 0, 0)
;	SetTemplateSizeVel(p\Objects\Template, 0, 0)
	
;Case 24
;	SetTemplateSize(p\Objects\Template, 0, 0, 0, 0)
;	SetTemplateSizeVel(p\Objects\Template, 0, 0)
;Case 25
;	SetTemplateSize(p\Objects\Template, 0.7, 0.7, 0.7, 0.7)
;	SetTemplateSizeVel(p\Objects\Template, 0.3, 1.0)
;	SetTemplateParticleLifeTime(p\Objects\Template, 2/d\delta, 2/d\delta)
	
;End Select
	
	
End Function
;~IDEal Editor Parameters:
;~C#Blitz3D