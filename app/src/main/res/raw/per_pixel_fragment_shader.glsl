precision mediump float;

uniform sampler2D u_Texture;
uniform float fraction;
uniform int isFadingPx; // flagga för om texturen håller på att göra en fade

varying vec2 v_TexCoordinate[2];

vec4 PixelColor[2];

void main()                    		
{                              

    //FADE

    //villkor för att göra en "fade"
    if (isFadingPx == 1) {

        PixelColor[0] = texture2D(u_Texture,  v_TexCoordinate[0]);
        PixelColor[1] = texture2D(u_Texture,  v_TexCoordinate[1]);
        gl_FragColor = mix(PixelColor[0], PixelColor[1], fraction);
    } else {
        gl_FragColor = texture2D(u_Texture, v_TexCoordinate[0]);
    }

}                                                                     	

