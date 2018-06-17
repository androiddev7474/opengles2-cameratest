uniform mat4 u_MVPMatrix;		// A constant representing the combined model/view/projection matrix.      		       
uniform mat4 u_MVMatrix;		// A constant representing the combined model/view matrix.       		

attribute vec4 a_Position;		// Per-vertex position information we will pass in.
attribute vec4 a_Color;			// Per-vertex color information we will pass in. 				
attribute vec3 a_Normal;		// Per-vertex normal information we will pass in.      
attribute vec2 a_TexCoordinate; // Per-vertex texture coordinate information we will pass in.
		  
varying vec3 v_Position;		// This will be passed into the fragment shader.
varying vec4 v_Color;			// This will b  e passed into the fragment shader.
varying vec3 v_Normal;			// This will be passed into the fragment shader.  
varying vec2 v_TexCoordinate;   // This will be passed into the fragment shader.    		

uniform vec2 translate;
attribute vec4 position;

varying vec4 vertexColor;

vec2 text_coord;

// The entry point for our vertex shader.  
void main()
{

	v_Color = a_Color;

    text_coord = a_TexCoordinate * vec2(0.5, 0.5);
    text_coord = text_coord + vec2(0.0, 0.25);
    v_TexCoordinate = text_coord;

	vertexColor = vec4(0.9, 0.4, 0.9, 1.0);

	gl_Position = u_MVPMatrix * a_Position;

}                                                          