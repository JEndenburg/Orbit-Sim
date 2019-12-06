port module Main exposing(..)

import Browser
import Html exposing (Html, main_, header, footer, div, span, text)
import Html.Attributes exposing (id, class, width, height)
import Math.Matrix4 as Mat4 exposing (Mat4)
import Math.Vector3 as Vec3 exposing (Vec3, vec3)
import WebGL

type Model 
    = Connecting
    | Disconnected
    | Connected (List SimulationElement)

type Msg
    = OnConnected ()

type SimulationElement
    = Planet SimulationElementData PlanetData
    | Vessel SimulationElementData

type alias SimulationElementData = 
    {   position : Vec3
    ,   velocity : Vec3
    ,   mass : Vec3
    }

type alias PlanetData = 
    {   radius : Float
    }


port receiveConnectedEvent : (() -> msg) -> Sub msg



main = Browser.document 
    {   init = init
    ,   view = view
    ,   update = update
    ,   subscriptions = subscriptions
    }

init : () -> (Model, Cmd Msg)
init _ = (Connecting, Cmd.none)

view : Model -> Browser.Document Msg
view model = 
    {   title = "Orbit Simulator"
    ,   body = 
        [   viewHeader
        ,   viewMain model
        ,   viewFooter
        ]
    }

update : Msg -> Model -> (Model, Cmd Msg)
update message model = 
    case message of
        OnConnected _ -> (Connected [], Cmd.none)

subscriptions : Model -> Sub Msg
subscriptions model =
    Sub.batch
    [   receiveConnectedEvent OnConnected
    ]



viewHeader : Html Msg
viewHeader = 
    header [] 
    [   span [id "page-title"] [text "Orbit Simulator"]
    ]

viewFooter : Html Msg
viewFooter = 
    footer []
    [   span [id "page-footer"] [text "footer text here"]
    ]

viewMain : Model -> Html Msg
viewMain model = 
    main_ [] 
    (   case model of
            Connecting -> viewConnecting
            Connected simulationElements -> viewConnected simulationElements
            Disconnected -> Debug.todo "Implement Disconnected state view"
    )

viewConnecting : List (Html Msg)
viewConnecting = 
    [   div [id "loading-wheel"] []
    ,   div [id "loading-text"] [text "Connecting to server..."]
    ]

viewConnected : (List SimulationElement) -> List (Html Msg)
viewConnected simulationElements =
    [   WebGL.toHtml
        [   width 500
        ,   height 500
        ]
        [   WebGL.entity vertexShader fragmentShader planetMesh (uniforms (vec3 0 0 0, vec3 180 70 30))
        ]
    ]



type alias Vertex =
    {   color : Vec3
    ,   position : Vec3
    }

planetMesh : WebGL.Mesh Vertex
planetMesh = 
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
        [   face (vec3 255 255 255) rft rfb rbb rbt
        ,   face (vec3 255 255 255) rft rfb lfb lft
        ,   face (vec3 255 255 255) rft lft lbt rbt
        ,   face (vec3 255 255 255) rfb lfb lbb rbb
        ,   face (vec3 255 255 255) lft lfb lbb lbt
        ,   face (vec3 255 255 255) rbt rbb lbb lbt
        ]

face : Vec3 -> Vec3 -> Vec3 -> Vec3 -> Vec3 -> List ( Vertex, Vertex, Vertex )
face color a b c d = 
    [   ({color = color, position = a}, {color = color, position = b}, {color = color, position = c})
    ,   ({color = color, position = c}, {color = color, position = d}, {color = color, position = a})
    ]

type alias Uniforms =
    {   rotation : Mat4
    ,   perspective : Mat4
    ,   camera : Mat4
    }

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
        ,   camera = Mat4.makeLookAt (vec3 0 0 5) (vec3 0 0 0) (vec3 0 1 0)
        }

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