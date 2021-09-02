import json


try:
    with open("config/config.json") as json_file:
            json_data = json.load(json_file)
            if json_data is not None:
                print(json_data["get_url"])
                print(json_data["other_operation_url"])
                json_file.close()
            else:
                print("json file is empty")
except IOError:
    print("file not found")

except ValueError:
    print("No information from Config file")