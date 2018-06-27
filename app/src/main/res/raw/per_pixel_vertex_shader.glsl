uniform mat4 u_MVPMatrix;
uniform mat4 u_MVMatrix;
uniform vec2 offset_vec, offset_vec2; // dessa vektorer används till att justera den ursprungliga texturkoordinaten som är högst upp i vänstra hörnet till nästa koordinat i texturatlasen
uniform int isFadingVt; // flagga för om texturen håller på att göra en fade

attribute vec4 a_Position;
attribute vec4 a_Color;
attribute vec3 a_Normal;
attribute vec2 a_TexCoordinate;

varying vec2 v_TexCoordinate[2];

vec2 text_coord[2]; //nya texturkoordinater som är anpassade till den aktuella texturatlasen. Hårdkodat i nuläget

void main()
{

    text_coord[0] = a_TexCoordinate * vec2(0.5, 0.5);
    text_coord[0] = text_coord[0] + offset_vec;
    v_TexCoordinate[0] = text_coord[0];

    if (isFadingVt == 1) {

       text_coord[1] = a_TexCoordinate * vec2(0.5, 0.5);
        text_coord[1] = text_coord[1] + offset_vec2;
        v_TexCoordinate[1] = text_coord[1];
    }

	gl_Position = u_MVPMatrix * a_Position;
}                                                          