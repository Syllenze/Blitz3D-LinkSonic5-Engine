; l_surface by Daniel Wooden
; Created : 10/25/2005

Global l_surface = CreateSprite( camera )
Global l_alpha# = 0.3
SpriteViewMode l_surface, 2
PositionEntity l_surface, 0, 0, 0.00638
ScaleSprite l_surface, 0.0064, 0.0048
EntityAlpha l_surface, l_alpha#

Global l_texture = CreateTexture( 640, 480, 1+256 )
ScaleTexture l_texture, 1.598, 1.066
TextureBlend l_texture, 2

EntityTexture l_surface, l_texture

Function l_update()
	
	RenderWorld
	CopyRect 0, 0, 640, 480, 0, 0, BackBuffer(), TextureBuffer( l_texture )
	
End Function

Function l_setAlpha( a# )
	
	EntityAlpha l_surface, a#
	
End Function