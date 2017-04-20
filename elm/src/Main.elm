module Main exposing (..)

import Html exposing (..)
import Http
import Resource exposing (..)
import Task


type Msg
    = GetResource
    | GetResourceResult (Result Http.Error Resource)


type alias Model =
    { json : String
    }


init : ( Model, Cmd Msg )
init =
    ( { json = "" }
    , Task.perform identity (Task.succeed GetResource)
      --getResource GetResourceResult
    )


update : Msg -> Model -> ( Model, Cmd Msg )
update msg model =
    case msg of
        GetResourceResult (Ok text) ->
            ( { model | json = (toString text) }, Cmd.none )

        GetResourceResult (Err error) ->
            ( { model | json = (toString error) }, Cmd.none )

        GetResource ->
            ( model, getResource GetResourceResult )


view : Model -> Html Msg
view model =
    div []
        [ text model.json
        ]


subscriptions : Model -> Sub Msg
subscriptions model =
    Sub.none


main : Program Never Model Msg
main =
    Html.program
        { init = init
        , update = update
        , view = view
        , subscriptions = subscriptions
        }
