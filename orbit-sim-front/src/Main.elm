port module Main exposing(..)

import Browser
import Html exposing (Html, main_, header, footer, div, span, text)
import Html.Attributes exposing (id, class, width, height)
import Math.Matrix4 as Mat4 exposing (Mat4)
import Math.Vector3 as Vec3 exposing (Vec3, vec3)
import WebGL

import Rendering.Renderer as Renderer

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
        [   Renderer.renderCube (vec3 1 0 0, vec3 25 15 54)
        ]
    ]