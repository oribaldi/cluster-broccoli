module Views.Body exposing (view)

import Views.TemplateView as TemplateView
import Models.Resources.Instance exposing (Instance, InstanceId)
import Models.Resources.Role exposing (Role)
import Models.Resources.Template exposing (TemplateId, Template, addTemplateInstanceString)
import Models.Ui.BodyUiModel exposing (BodyUiModel)
import Updates.Messages exposing (UpdateBodyViewMsg(..))
import Dict exposing (Dict)
import Html exposing (..)
import Html.Attributes exposing (..)


view : Dict TemplateId Template -> Dict InstanceId Instance -> BodyUiModel -> Maybe Role -> Html UpdateBodyViewMsg
view templates instances bodyUiModel maybeRole =
    div
        [ class "container" ]
        (templates
            |> Dict.values
            |> List.map (TemplateView.view instances templates bodyUiModel maybeRole)
        )
