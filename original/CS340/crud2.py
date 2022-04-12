from pymongo import MongoClient
from bson.objectid import ObjectId
from pprint import pprint


class AnimalShelter(object):
    """ CRUD operations for Animal collection in MongoDB """

    def __init__(self, username, password, dbname):
        # Initializing the MongoClient. This helps to
        # access the MongoDB databases and collections.
        self.client = MongoClient(
            'mongodb://%s:%s@localhost:52044/?authMechanism=DEFAULT&authSource=%s' % (username, password, dbname))
        
        # Set database and list collection in database
        self.database = self.client[dbname]
        
        # Set collection to use
        collname = 'animals'
        self.collection = self.database[collname]
        
# Create method to implement the C in CRUD.
    def create(self, data):
        if data is not None:
            return self.collection.insert(data)  # data should be a list of one or more dictionary
        else:
            raise Exception("[-] ERROR: Nothing to save, because data parameter is empty.")

# Read method to implement the R in CRUD.
    def read(self, data):
        if data is not None:
            return self.collection.find(data, {"_id":False})  # data should be dictionary
        else:
            raise Exception("[-] ERROR: Nothing to read, because data parameter is empty.")

# Update method to implement the U in CRUD.
    def update(self, data):
        if data is not None:
            return self.collection.update_many(data)  # data should be dictionary
        else:
            raise Exception("[-] ERROR: Nothing to update, because data parameter is empty.")

# Delete method to implement the D in CRUD.
    def delete(self, data):
        if data is not None:
            return self.collection.remove(data)  # data should be dictionary
        else:
            raise Exception("[-] ERROR: Nothing to delete, because data parameter is empty.")