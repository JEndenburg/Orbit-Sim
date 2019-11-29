port module Main exposing(..)

import Browser
import Html exposing (Html, main_, header, footer, div, span, text)
import Html.Attributes exposing (id, class)
import Math.Vector3 as Vec3 exposing (Vec3, vec3)

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
            Connected simulationElements -> Debug.todo "Implement Connected state view"
            Disconnected -> Debug.todo "Implement Disconnected state view"
    )

viewConnecting : List (Html Msg)
viewConnecting = 
    [   div [id "loading-wheel"] []
    ,   div [id "loading-text"] [text "Connecting to server..."]
    ]