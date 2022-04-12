# -*- coding: utf-8 -*pip install 
from jupyter_plotly_dash import JupyterDash

import dash
import dash_leaflet as dl
import dash_core_components as dcc
import dash_html_components as html
import plotly.express as px
import dash_table as dt
from dash.dependencies import Input, Output, State

import os
import numpy as np
import pandas as pd
from pymongo import MongoClient
from bson.json_util import dumps

#### DONE #####
# change animal_shelter and AnimalShelter to match your CRUD Python module file name and class name
from crud import AnimalShelter
# image encoder
import base64


###########################
# Data Manipulation / Model
###########################
# DONE: change for your username and password and CRUD Python module name
username = "aacuser"
password = "cs340"
dbname = "AAC"
shelter = AnimalShelter(username, password, dbname)

# class read method must support return of cursor object 
df = pd.DataFrame.from_records(shelter.read({}))


#########################
# Dashboard Layout / View
#########################
#for testing in Jupyter Notebook
#app = JupyterDash('Salvare Search for Rescue Web App')

#for running in computer terminal
app = dash.Dash('Salvare Search for Rescue Wen App')

#DONE: Add in Grazioso Salvare’s logo
image_filename = 'GraziosoSalvareLogo.png'
encoded_image = base64.b64encode(open(image_filename, 'rb').read())

app.layout = html.Div([
    html.Div(id='hidden-div', style={'display':'none'}),
#DONE: Place the HTML image tag in the line below into the app.layout code according to your design
    html.Center([
        # customer image location with anchor tag to the client’s home page: www.snhu.edu.
        html.A([
            html.Img(id='customer-image',
                     src='data:image/png;base64,{}'.format(encoded_image.decode()),
                     alt='Grazioso Salvare Logo',
                     style={'width': 225})
        ], href="https://www.arsari.us", target="_blank"),
#DONE: Also remember to include a unique identifier such as your name or date
        html.H1("Animal Shelter Search Dashboard"),
        html.H5("Developed by Arturo Santiago-Rivera", style={'color': 'green'})
    ]),
    html.Hr(),
#DONE: Add in code for the interactive filtering options. For example, Radio buttons, drop down, checkboxes, etc.
    # buttons at top of table to filter the data set to find cats or dogs
    html.Div(className='row',
        style={'display' : 'flex'},
        children=[
            html.Span("Filter by:", style={'margin': 6}),
            html.Span(
                html.Button(id='submit-button-one', n_clicks=0, children='Cats'),
                style={'margin': 6}
            ),
            html.Span(
                html.Button(id='submit-button-two', n_clicks=0, children='Dogs'),
                style={'margin': 6}
            ),
            html.Span(
                html.Button(id='reset-buttons', n_clicks=0, children='Reset', style={'background-color': 'red', 'color': 'white'}),
                style={'margin': 6,}
            ),
            html.Span("or", style={'margin': 6}),
            html.Span([
                dcc.Dropdown(
                    id='filter-type',
                    options=[
                        {'label': 'Water Rescue', 'value': 'wr'},
                        {'label': 'Mountain or Wilderness Rescue', 'value': 'mwr'},
                        {'label': 'Disaster Rescue or Individual Tracking', 'value': 'drit'}
                    ],
                    placeholder="Select a Dog Category Filter",
                    style={'marginLeft': 5, 'width': 350}
                )
            ])
        ]
    ),
    html.Hr(),
    dt.DataTable(
        id='datatable-id',
        columns=[
            {"name": i, "id": i, "deletable": False, "selectable": True} for i in df.columns
        ],
        data=df.to_dict('records'),
#DONE: Set up the features for your interactive data table to make it user-friendly for your client
#If you completed the Module Six Assignment, you can copy in the code you created here
        editable = False,
        filter_action = "native",
        sort_action = "native",
        sort_mode = "multi",
        column_selectable = False,
        row_selectable = False,
        row_deletable = False,
        selected_columns = [],
        selected_rows = [0],
        page_action = "native",
        page_current = 0,
        page_size = 10,        
    ),
    html.Br(),
    html.Hr(),
#This sets up the dashboard so that your chart and your geolocation chart are side-by-side
    html.Div(className='row',
         style={'display' : 'flex'},
         children=[
            html.Div(
                id='graph-id',
                className='col s12 m6',
            ),
            html.Div(
                id='map-id',
                className='col s12 m6',
            )
         ]
    ),
#DONE: Also remember to include a unique identifier such as your name or date (footer identifier)
    html.Div([
        html.Hr(),
        html.P([
            "Module 7-2 Project Two Submission - Prof. Tad Kellogg M.S.",
            html.Br(),
            "CS-340 Client/Server Development 21EW3 - Southern New Hampshire University",
            html.Br(),
            "February 21, 2021"
        ], style={'fontSize': 12})
    ])
])


#############################################
# Interaction Between Components / Controller
#############################################

# DONE: This callback add interactive dropdown filter option to the dashboard to find dogs per category
# or interactive button filter option to the dashboard to find all cats or all dogs
@app.callback(
    Output('datatable-id', 'data'),
    [Input('filter-type', 'value'),
     Input('submit-button-one', 'n_clicks'),
     Input('submit-button-two', 'n_clicks')]
)
def update_dshboard(selected_filter, btn1, btn2):
    if (selected_filter == 'drit'):
        df = pd.DataFrame(list(shelter.read(
                {
                    "animal_type":"Dog",
                    "breed":{"$in":["Doberman Pinscher","German Shepherd","Golden Retriever","Bloodhound","Rottweiler"]},                   "sex_upon_outcome":"Intact Male",
                    "age_upon_outcome_in_weeks": {"$gte":20},
                    "age_upon_outcome_in_weeks":{"$lte":300}
                }
            )
        ))
    elif (selected_filter == 'mwr'):
        df = pd.DataFrame(list(shelter.read(
                {
                    "animal_type":"Dog",
                    "breed":{"$in":["German Shepherd","Alaskan Malamute","Old English Sheepdog","Siberian Husky","Rottweiler"]},
                    "sex_upon_outcome":"Intact Male",
                    "age_upon_outcome_in_weeks":{"$gte":26},
                    "age_upon_outcome_in_weeks":{"$lte":156}
                }
            )
        ))
    elif (selected_filter == 'wr'):
        df = pd.DataFrame(list(shelter.read(
                {
                    "animal_type":"Dog",
                    "breed":{"$in":["Labrador Retriever Mix","Chesapeake Bay Retriever","Newfoundland"]},
                    "sex_upon_outcome":"Intact Female",
                    "age_upon_outcome_in_weeks":{"$gte":26},
                    "age_upon_outcome_in_weeks":{"$lte":156}
                }
            )
        ))
    # higher number of button clicks to determine filter type
    elif (int(btn1) > int(btn2)):
        df = pd.DataFrame(list(shelter.read({"animal_type":"Cat"})))
    elif (int(btn2) > int(btn1)):
        df = pd.DataFrame(list(shelter.read({"animal_type":"Dog"})))
    else:
        df = pd.DataFrame.from_records(shelter.read({}))

    data = df.to_dict('records')

    return data

# This callback reset the clicks of the cat and dog filter button
@app.callback(
    [Output('submit-button-one', 'n_clicks'),
     Output('submit-button-two', 'n_clicks')],
    [Input('reset-buttons', 'n_clicks')]
)
def update(reset):
    return 0, 0

# This callback will highlight a column or row on the data table when the user, at first, selects it on the currently visible page
@app.callback(
    Output('datatable-id', 'style_data_conditional'),
    [Input('datatable-id', 'selected_columns'),
     Input('datatable-id', "derived_viewport_selected_rows"),
     Input('datatable-id', 'active_cell')]
)
def update_styles(selected_columns, selected_rows, active_cell):
    if active_cell is not None:
        style = [{
                    'if': { 'row_index': active_cell['row'] },
                    'background_color':'#a5d6a7'
                }]
    else:
        style = [{
                    'if': { 'row_index': i },
                    'background_color':'#a5d6a7'
                } for i in selected_rows]
        
    return (style +
                [{
                    'if': { 'column_id': i },
                    'background_color': '#80deea'
                } for i in selected_columns]
            )

# This callback add a pie chart that displays breed percentage from the interactive data table
@app.callback(
    Output('graph-id', "children"),
    [Input('datatable-id', "derived_viewport_data")]
)
def update_graphs(viewData):
    ### DONE: ####
    dff = pd.DataFrame.from_dict(viewData)
    
    # code for pie chart
    fig = px.pie(
        dff,
        names='breed',
        title='Animal Breeds Pie Chart'
    )
    
    return [dcc.Graph(figure=fig)]

# This callback add a geolocation chart that displays data from the interactive data table
@app.callback(
    Output('map-id', "children"),
    [Input('datatable-id', "derived_viewport_data"),
     Input('datatable-id', "derived_viewport_selected_rows"),
     Input('datatable-id', "active_cell")]
)
def update_map(viewData, selected_rows, active_cell):
# DONE: Add in the code for your geolocation chart
    dff = pd.DataFrame.from_dict(viewData)

    # define marker position of one selected row
    if active_cell is not None:
        row = active_cell['row']
    else:
        row = selected_rows[0]
        
    lat = dff.loc[row,'location_lat']
    long = dff.loc[row,'location_long']
    name = dff.loc[row,'name']
    breed = dff.loc[row,'breed']
    animal = dff.loc[row, 'animal_type']
    age = dff.loc[row, 'age_upon_outcome']
    
    if name == "":
        name = "No Name"
    
    return [
        dl.Map(
            style={'width': '1000px', 'height': '500px'},
            center=[lat,long], zoom=10,
            children=[
                dl.TileLayer(id="base-layer-id"),
                # Marker with tool tip and popup
                dl.Marker(
                    position=[lat,long],
                    children=[
                        dl.Tooltip("({:.3f}, {:.3f})".format(lat,long)),
                        dl.Popup([
                            html.H2(name),
                            html.P([
                                html.Strong("{} | Age: {}".format(animal,age)),
                                html.Br(),
                                breed])
                        ])
                    ]
                )
            ]
        )
    ]


###############
# App execution
###############
#for testing in Jupyter Notebook
#app

#for running in computer terminal
if __name__ == '__main__':
   app.run_server(debug=True)