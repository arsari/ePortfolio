from pymongo import MongoClient
from bson.objectid import ObjectId
from pprint import pprint

class AnimalShelter(object):
    """ CRUD operations for Animal collection in MongoDB """

    def __init__(self, username, password, dbname):
        # Please change the port to the localhost.
        port = 27017
        # Initializing the MongoClient. This helps to
        # access the MongoDB databases and collections.
        self.client = MongoClient(
            'mongodb://%s:%s@localhost:%d/?authMechanism=DEFAULT&authSource=%s' % (username, password, port, dbname))
        print("[+] User Authenticated in Database [ %s ]" % dbname)
        
        # Set database and list collection in database
        self.database = self.client[dbname]
        print("\n[+] List of collections")
        print("-----------------------")
        for coll in self.database.list_collection_names(): # list collections in database
            print(coll)
        
        # Set collection to use
        collname = 'animals'
        print("\n[?] Enter collection to use: %s" % collname)
        print("\n[+] Collection to use < %s >" % collname)
        self.collection = self.database[collname]
        
        return print("\n*** Authentication Complete ***")

# Create method to implement the C in CRUD.
    def create(self, data):
        if data is not None:
            docs = self.collection.insert_many(data)  # data should be a list of one or more dictionary
            print("[+] Document Created Succesfully %s" % len(data))
            print("------------------------------------")
            for doc in docs.inserted_ids:  # iterate docs to list the ObjectId of the documents created
                print("ObjectId => %s" % doc)
            return print("------------------------------------\n*** End of List ***")
        else:
            raise Exception("[-] ERROR: Nothing to save, because data parameter is empty.")

# Read method to implement the R in CRUD.
    def read(self, data):
        if data is not None:
            docs = self.collection.find(data)  # data should be dictionary
            print("[+] Total of Documents Found %s" % self.collection.count_documents(data))  # count total of documents in docs
            print("--------------------------------")
            for doc in docs: # iterate docs to list documents founds
                pprint(doc)
            return print("------------------------------------\n*** End of List ***")
        else:
            raise Exception("[-] ERROR: Nothing to read, because data parameter is empty.")

# Update method to implement the U in CRUD.
    def update(self, data):
        if data is not None:
            docs = self.collection.update_many(*data)  # data should be dictionary
            print("[+] Total of Documents Updated %s" % docs.modified_count)  # count total of documents in updated
            print("---------------------------------")
            query = {'update': 'true'}
            for doc in self.collection.find(query):  # list documents updated base on query variable
                pprint(doc)
            self.database.animals.update_many({}, {'$unset': {'update': '1'}}) # remove update key from documents
            return print("------------------------------------\n*** End of List ***")
        else:
            raise Exception("[-] ERROR: Nothing to update, because data parameter is empty.")

# Delete method to implement the D in CRUD.
    def delete(self, data):
        if data is not None:
            docs = self.collection.delete_many(data)  # data should be dictionary
            return print("[+] Total of Documents Deleted %s" % docs.deleted_count)  # count total of documents in docs
        else:
            raise Exception("[-] ERROR: Nothing to delete, because data parameter is empty.")