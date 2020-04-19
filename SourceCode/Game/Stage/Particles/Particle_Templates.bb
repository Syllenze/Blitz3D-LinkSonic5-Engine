;--------------------
; Particle templates
;--------------------

Type tParticleGroup
	Field Tmp1
	Field Tmp2
	Field Tmp3
	Field Tmp4
	Field Tmp5
	Field Tmp6
	Field Tmp7
	Field Tmp8
End Type

Function LoadParticleTemplates.tParticleGroup(group=0)

Select group
Case 0
CommonParticles.tParticleGroup = New tParticleGroup
	; Spindash
	CommonParticles\Tmp1 = CreateTemplate()
		SetTemplateEmitterBlend(CommonParticles\Tmp1, 1)
		SetTemplateInterval(CommonParticles\Tmp1, 4)
		SetTemplateEmitterLifeTime(CommonParticles\Tmp1, 20)
		SetTemplateParticleLifeTime(CommonParticles\Tmp1, 160, 170)
		SetTemplateTexture(CommonParticles\Tmp1, "Textures\Dust.png", 3, 3)
		SetTemplateOffset(CommonParticles\Tmp1, -.1, .1, -.1, .1, -.1, .1)
		SetTemplateVelocity(CommonParticles\Tmp1, -0.2, 0.2, -.06, -.02, -.02, .02)
		SetTemplateAlphaVel(CommonParticles\Tmp1, True)
		SetTemplateSize(CommonParticles\Tmp1, 0.4, 0.4, 1.0, 1.5)
		SetTemplateSizeVel(CommonParticles\Tmp1, .01, 1.01, 0.01)
	;	SetTemplateRotation(CommonParticles\Tmp1, -5, 5)
		SetTemplateGravity(CommonParticles\Tmp1, -0.002)
		SetTemplateAlignToFall(CommonParticles\Tmp1, PlayerRotation)
	
	; SpindashRelease
	CommonParticles\Tmp2 = CreateTemplate()
		SetTemplateEmitterBlend(CommonParticles\Tmp2, 1)
		SetTemplateInterval(CommonParticles\Tmp2, 2)
		SetTemplateEmitterLifeTime(CommonParticles\Tmp2, 40)
		SetTemplateParticleLifeTime(CommonParticles\Tmp2, 160, 170)
		SetTemplateTexture(CommonParticles\Tmp2, "Textures\Dust.png", 3, 3)
		SetTemplateOffset(CommonParticles\Tmp2, -.1, .1, -.1, .1, -.1, .1)
		SetTemplateVelocity(CommonParticles\Tmp2, -.05, .05, -.06, -.02, -.05, .05)
		SetTemplateAlphaVel(CommonParticles\Tmp2, True)
		SetTemplateSize(CommonParticles\Tmp2, 0.6, 0.6, 1.0, 1.5)
		SetTemplateSizeVel(CommonParticles\Tmp2, .08, 1.01, 0.2)
		SetTemplateRotation(CommonParticles\Tmp2, -5, 5)
		SetTemplateGravity(CommonParticles\Tmp2, -0.002)
	
	; Skidding
	CommonParticles\Tmp3 = CreateTemplate()
		SetTemplateEmitterBlend(CommonParticles\Tmp3, 1)
		SetTemplateInterval(CommonParticles\Tmp3, 8)
		SetTemplateEmitterLifeTime(CommonParticles\Tmp3, 20)
		SetTemplateParticleLifeTime(CommonParticles\Tmp3, 100, 120)
		SetTemplateTexture(CommonParticles\Tmp3, "Textures\Dust.png", 3, 3)
		SetTemplateOffset(CommonParticles\Tmp3, -.1, .1, -2, -2, -.1, .1)
		SetTemplateVelocity(CommonParticles\Tmp3, -.05, .05, -.06, -.02, -.05, .05)
		SetTemplateAlphaVel(CommonParticles\Tmp3, True)
		SetTemplateSize(CommonParticles\Tmp3, 0.4, 0.4, 1.0, 1.5)
		SetTemplateSizeVel(CommonParticles\Tmp3, .01, 1.01, 0.2)
		SetTemplateRotation(CommonParticles\Tmp3, -5, 5)
		SetTemplateGravity(CommonParticles\Tmp3, -0.002)
		
	CommonParticles\Tmp4 = FogTemplate()
	CommonParticles\Tmp5 = WaterfallTemplate2()
		
	; Farting
	CommonParticles\Tmp8 = CreateTemplate()
		SetTemplateEmitterBlend(CommonParticles\Tmp8, 1)
		SetTemplateInterval(CommonParticles\Tmp8, 16)
		SetTemplateEmitterLifeTime(CommonParticles\Tmp8, 20)
		SetTemplateParticleLifeTime(CommonParticles\Tmp8, 50, 60)
		SetTemplateTexture(CommonParticles\Tmp8, "Textures\Dust.png", 3, 3)
		SetTemplateOffset(CommonParticles\Tmp8, -.1, .1, -2, -2, -.1, .1)
		SetTemplateVelocity(CommonParticles\Tmp8, -.05, .05, -.06, -.02, -.05, .05)
		SetTemplateAlphaVel(CommonParticles\Tmp8, True)
		SetTemplateSize(CommonParticles\Tmp8, 1.0, 1.0, 4.0, 4.0)
		SetTemplateSizeVel(CommonParticles\Tmp8, .01, 1.01, 1)
		SetTemplateRotation(CommonParticles\Tmp8, -5, 5)
		SetTemplateGravity(CommonParticles\Tmp8, -0.006)
		SetTemplateColors(CommonParticles\Tmp8, $44AA00, $668800)
		SetTemplateMaxParticles(CommonParticles\Tmp8, 500)
		
Return CommonParticles

Case 1
LavaReefParticles.tParticleGroup = New tParticleGroup
	; Fireworm
	LavaReefParticles\Tmp3 = CreateTemplate()
		SetTemplateInterval(LavaReefParticles\Tmp3, 1)
		SetTemplateEmitterLifeTime(LavaReefParticles\Tmp3, 2)
		SetTemplateParticleLifeTime(LavaReefParticles\Tmp3, 5, 10)
		SetTemplateTexture(LavaReefParticles\Tmp3, "Textures\Fire.jpg", 3, 3)
		SetTemplateOffset(LavaReefParticles\Tmp3, -.1, .1, 1, 1, -.1, .1)
		SetTemplateVelocity(LavaReefParticles\Tmp3, -.05, .05, .3, .6, -.05, .05)
		SetTemplateAlphaVel(LavaReefParticles\Tmp3, True)
		SetTemplateSize(LavaReefParticles\Tmp3, 2.0, 2.0)
		SetTemplateColors(LavaReefParticles\Tmp3, $FF8844, $FF4400)
		
	; Iwamodoki
	LavaReefParticles\Tmp4 = CreateTemplate()
		SetTemplateEmitterBlend(LavaReefParticles\Tmp4, 1)
		SetTemplateInterval(LavaReefParticles\Tmp4, 1)
		SetTemplateParticlesPerInterval(LavaReefParticles\Tmp4, 8)
		SetTemplateEmitterLifeTime(LavaReefParticles\Tmp4, 1)
		SetTemplateParticleLifeTime(LavaReefParticles\Tmp4, 60, 75)
		SetTemplateTexture(LavaReefParticles\Tmp4, "Textures\Stone.png", 4)
		SetTemplateOffset(LavaReefParticles\Tmp4, -.4, .4, -.4, .4, -.4, .4)
		SetTemplateVelocity(LavaReefParticles\Tmp4, -.5, .5, 0.5, 1.0, -.5, .5)
		SetTemplateRotation(LavaReefParticles\Tmp4, -3, 3)
		SetTemplateGravity(LavaReefParticles\Tmp4, .03)
		SetTemplateSize(LavaReefParticles\Tmp4, .4, .4, .5, 2)
		SetTemplateFloor(LavaReefParticles\Tmp4, -5, .45)
	
	; Toxomister
	LavaReefParticles\Tmp1 = CreateTemplate()
		SetTemplateEmitterBlend(LavaReefParticles\Tmp1, 1)
		SetTemplateInterval(LavaReefParticles\Tmp1, 4)
		SetTemplateEmitterLifeTime(LavaReefParticles\Tmp1, -1)
		SetTemplateParticleLifeTime(LavaReefParticles\Tmp1, 160, 170)
		SetTemplateTexture(LavaReefParticles\Tmp1, "Textures\Particle-034.png", 3, 3)
		SetTemplateOffset(LavaReefParticles\Tmp1, -.1, .1, -.1, .1, -.1, .1)
		SetTemplateVelocity(LavaReefParticles\Tmp1, -.08, .08, -.06, -.02, -.08, .08)
		SetTemplateAlphaVel(LavaReefParticles\Tmp1, True)
		SetTemplateSize(LavaReefParticles\Tmp1, 0.5, 0.5, 1.5, 2.5)
		SetTemplateSizeVel(LavaReefParticles\Tmp1, .01, 1.01, 1)
		SetTemplateRotation(LavaReefParticles\Tmp1, -3, 3)
		SetTemplateGravity(LavaReefParticles\Tmp1, -0.0002)
	
	; Fire 1
	LavaReefParticles\Tmp2 = CreateTemplate()
		SetTemplateInterval(LavaReefParticles\Tmp2, 1)
		SetTemplateEmitterLifeTime(LavaReefParticles\Tmp2, -1)
		SetTemplateParticleLifeTime(LavaReefParticles\Tmp2, 10, 25)
		SetTemplateTexture(LavaReefParticles\Tmp2, "Textures\Fire.jpg", 3, 3)
		SetTemplateOffset(LavaReefParticles\Tmp2, -.5, .5, -.5, .5, -.5, .5)
		SetTemplateVelocity(LavaReefParticles\Tmp2, -.2, .2, .3, .6, -.2, .2)
		SetTemplateAlphaVel(LavaReefParticles\Tmp2, True)
		SetTemplateSize(LavaReefParticles\Tmp2, 4.0, 4.0)
		SetTemplateColors(LavaReefParticles\Tmp2, $FF8844, $FF4400)
		
		Return LavaReefParticles
		
	Case 3
			; Skidding
		CommonParticles\Tmp3 = CreateTemplate()
		SetTemplateEmitterBlend(CommonParticles\Tmp3, 1)
		SetTemplateInterval(CommonParticles\Tmp3, 8)
		SetTemplateEmitterLifeTime(CommonParticles\Tmp3, 20)
		SetTemplateParticleLifeTime(CommonParticles\Tmp3, 100, 120)
		SetTemplateTexture(CommonParticles\Tmp3, "Textures\Dust.png", 3, 3)
		SetTemplateOffset(CommonParticles\Tmp3, -.1, .1, -2, -2, -.1, .1)
		SetTemplateVelocity(CommonParticles\Tmp3, -.05, .05, -.06, -.02, -.05, .05)
		SetTemplateAlphaVel(CommonParticles\Tmp3, True)
		SetTemplateSize(CommonParticles\Tmp3, 0.4, 0.4, 1.0, 1.5)
		SetTemplateSizeVel(CommonParticles\Tmp3, .01, 1.01, 1)
		SetTemplateRotation(CommonParticles\Tmp3, -5, 5)
		SetTemplateGravity(CommonParticles\Tmp3, -0.002)
		
		Return CommonParticles
End Select
End Function

Function LoadTemplates(group=0)

	Select group
	
Case 0
; Toxomister
pToxomister = CreateTemplate()
SetTemplateEmitterBlend(pToxomister, 1)
SetTemplateInterval(pToxomister, 4)
SetTemplateEmitterLifeTime(pToxomister, -1)
SetTemplateParticleLifeTime(pToxomister, 160, 170)
SetTemplateTexture(pToxomister, "Textures\Particle-034.png", 3, 3)
SetTemplateOffset(pToxomister, -.1, .1, -.1, .1, -.1, .1)
SetTemplateVelocity(pToxomister, -.08, .08, -.06, -.02, -.08, .08)
SetTemplateAlphaVel(pToxomister, True)
SetTemplateSize(pToxomister, 0.5, 0.5, 1.5, 2.5)
SetTemplateSizeVel(pToxomister, .01, 1.01, 1)
SetTemplateRotation(pToxomister, -3, 3)
SetTemplateGravity(pToxomister, -0.0002)

; Fire1
pFire1 = CreateTemplate()
SetTemplateEmitterBlend(pToxomister, 1)
SetTemplateInterval(pToxomister, 4)
SetTemplateEmitterLifeTime(pToxomister, -1)
SetTemplateParticleLifeTime(pToxomister, 160, 170)
SetTemplateTexture(pToxomister, "Textures\Particle-034.png", 3, 3)
SetTemplateOffset(pToxomister, -.1, .1, -.1, .1, -.1, .1)
SetTemplateVelocity(pToxomister, -.08, .08, -.06, -.02, -.08, .08)
SetTemplateAlphaVel(pToxomister, True)
SetTemplateSize(pToxomister, 0.5, 0.5, 1.5, 2.5)
SetTemplateSizeVel(pToxomister, .01, 1.01, 1)
SetTemplateRotation(pToxomister, -3, 3)
SetTemplateGravity(pToxomister, -0.0002)

;Template1 - Fire
tmp1 = CreateTemplate()
SetTemplateInterval(tmp1, 1)
SetTemplateEmitterLifeTime(tmp1, -1)
SetTemplateParticleLifeTime(tmp1, 10, 25)
SetTemplateTexture(tmp1, "Textures\Fire.jpg", 3, 3)
SetTemplateOffset(tmp1, -.5, .5, -.5, .5, -.5, .5)
SetTemplateVelocity(tmp1, -.2, .2, .3, .6, -.2, .2)
SetTemplateAlphaVel(tmp1, True)
SetTemplateSize(tmp1, 4.0, 4.0)
SetTemplateColors(tmp1, $FF8844, $FF4400)

;Template2 - Sparks
tmp2 = CreateTemplate()
SetTemplateInterval(tmp2, 10)
SetTemplateEmitterLifeTime(tmp2, -1)
SetTemplateParticleLifeTime(tmp2, 5, 20)
SetTemplateTexture(tmp2, "Textures\Spark.jpg", 2, 3)
SetTemplateOffset(tmp2, -.45, .45, .3, .5, -.45, .45)
SetTemplateVelocity(tmp2, -.065, .065, .1, .27, -.065, .065)
SetTemplateGravity(tmp2, .01)
SetTemplateAlphaVel(tmp2, False)
SetTemplateSize(tmp2, .055, .055)
SetTemplateColors(tmp2, $FFBB00, $FFFFFF)
SetTemplateBrightness(tmp2, 5)

	End Select
	
End Function

Function FogTemplate()
;Create a template with the following properties:
;the commands like SetEmitter...() will be explained soon!
template = CreateTemplate()
SetTemplateMaxParticles(template, 10)
SetTemplateEmitterBlend(template, 3)
SetTemplateInterval(template, 10)
SetTemplateEmitterLifeTime(template, -1)
SetTemplateParticleLifeTime(template, 50, 100)
SetTemplateTexture(template, "Textures\Particle-030.png", 2, 1)
SetTemplateOffset(template, -.2, .2, -.1, .1, -.2, .2) ; offset spawning of particles (minX, maxX, minY, maxY...)
SetTemplateVelocity(template, -.1, .1, .05, .1, -.1, .1) ; initial starting and ending velocity (minX, maxX, minY, maxY...)
SetTemplateAlphaVel(template, True)
SetTemplateSize(template, 4.0, 4.0, 1.0, 2.0)
SetTemplateSizeVel(template, .001, 1.01, 1)
SetTemplateRotation(template, -1, 1)
SetTemplateGravity(template, 0.001)
SetTemplateColors(template, $CCFFFF, $FFFFFF)
Return template
End Function

Function WaterfallTemplate2()
template = CreateTemplate()
SetTemplateMaxParticles(template, 10)
SetTemplateEmitterBlend(template, 3)
SetTemplateInterval(template, 10)
SetTemplateEmitterLifeTime(template, -1)
SetTemplateParticleLifeTime(template, 50, 75)
SetTemplateTexture(template, "Textures\Particle-030.png", 2, 1)
SetTemplateOffset(template, -.2, .2, -.1, .1, -.2, .2) ; offset spawning of particles (minX, maxX, minY, maxY...)
SetTemplateVelocity(template, -.3, .3, .001, .002, -.3, .3) ; initial starting and ending velocity (minX, maxX, minY, maxY...)
SetTemplateAlphaVel(template, True)
SetTemplateSize(template, 8.0, 8.0, 2.0, 3.0)
SetTemplateSizeVel(template, .0001, 1.01, 1)
SetTemplateRotation(template, -.1, .1)
SetTemplateGravity(template, .0)
SetTemplateColors(template, $CCFFFF, $FFFFFF)
Return template
End Function
;~IDEal Editor Parameters:
;~C#Blitz3D