#version 300 es
#include phong_functions_fsh

uniform highp vec3 camera_position;
uniform lowp vec4 material_diffuse_surface_color;
uniform lowp float material_diffuse_intensity;
uniform lowp float material_alpha;
uniform lowp float material_shininess;

uniform sampler2D specular_texture;

#pragma surface_modifier_uniforms
#pragma fragment_modifier_uniforms

in lowp mat3 v_tbn;
in highp vec2 v_texcoord;
in highp vec3 v_surface_position;

out lowp vec4 frag_color;

void main() {
    _surface.diffuse_color = material_diffuse_surface_color;
    _surface.diffuse_texcoord = v_texcoord;
    _surface.diffuse_intensity = material_diffuse_intensity;
    _surface.shininess = material_shininess;
    _surface.specular_texcoord = v_texcoord;
    _surface.alpha = material_alpha;
    _surface.normal = v_tbn[2];
    _surface.position = v_surface_position;

#pragma surface_modifier_body

    lowp vec4 _output_color = phong_lighting(_surface, camera_position, specular_texture);
    
#pragma fragment_modifier_body
    
    frag_color = _output_color;
}
