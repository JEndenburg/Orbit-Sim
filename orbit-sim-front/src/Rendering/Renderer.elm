module Rendering.Renderer exposing (renderCube)

import Math.Matrix4 as Mat4 exposing (Mat4)
import Math.Vector3 as Vec3 exposing (Vec3, vec3)
import WebGL

type alias Vertex =
    {   color : Vec3
    ,   position : Vec3
    }

type alias Uniforms =
    {   rotation : Mat4
    ,   perspective : Mat4
    ,   camera : Mat4
    }


renderCube : (Vec3, Vec3) -> Vec3 -> WebGL.Entity
renderCube transforms color = WebGL.entity vertexShader fragmentShader (cubeMesh color) (uniforms transforms)


uniforms : (Vec3, Vec3) -> Uniforms
uniforms (position, rotation) =
    let
        (xRot, yRot, zRot) = (Vec3.getX rotation, Vec3.getY rotation, Vec3.getZ rotation)
    in
        {   rotation = 
                Mat4.mul
                    (Mat4.mul
                        (Mat4.makeRotate (xRot) (vec3 1 0 0))
                        (Mat4.makeRotate (yRot) (vec3 0 1 0)))
                    (Mat4.makeRotate (zRot) (vec3 0 0 2))
        ,   perspective = Mat4.makePerspective 45 1 0.01 100
        ,   camera =
                Mat4.translate position (Mat4.makeLookAt (vec3 0 0 5) (vec3 0 0 0) (vec3 0 1 0))
        }

cubeMesh : Vec3 -> WebGL.Mesh Vertex
cubeMesh color = 
    let
        rft = vec3  1  1  1
        lft = vec3 -1  1  1
        lbt = vec3 -1 -1  1
        rbt = vec3  1 -1  1
        rbb = vec3  1 -1 -1
        rfb = vec3  1  1 -1
        lfb = vec3 -1  1 -1
        lbb = vec3 -1 -1 -1
    in
        WebGL.triangles <| List.concat
        [   face color rft rfb rbb rbt
        ,   face color rft rfb lfb lft
        ,   face color rft lft lbt rbt
        ,   face color rfb lfb lbb rbb
        ,   face color lft lfb lbb lbt
        ,   face color rbt rbb lbb lbt
        ]

face : Vec3 -> Vec3 -> Vec3 -> Vec3 -> Vec3 -> List ( Vertex, Vertex, Vertex )
face color a b c d = 
    [   ({color = color, position = a}, {color = color, position = b}, {color = color, position = c})
    ,   ({color = color, position = c}, {color = color, position = d}, {color = color, position = a})
    ]

vertexShader : WebGL.Shader Vertex Uniforms { vcolor : Vec3 }
vertexShader =
  [glsl|
    attribute vec3 position;
    attribute vec3 color;
    uniform mat4 perspective;
    uniform mat4 camera;
    uniform mat4 rotation;
    varying vec3 vcolor;
    void main () {
        gl_Position = perspective * camera * rotation * vec4(position, 1.0);
        vcolor = color;
    }
  |]


fragmentShader : WebGL.Shader {} Uniforms { vcolor : Vec3 }
fragmentShader =
  [glsl|
    precision mediump float;
    varying vec3 vcolor;
    void main () {
        gl_FragColor = 0.8 * vec4(vcolor, 1.0);
    }
  |]