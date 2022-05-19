import json

from flask import Flask, request
import cv2 as cv
from time import time_ns
import firebase_admin as fb
from firebase_admin import storage
from firebase_admin import db

# Initialising firebase storage
cred = fb.credentials.Certificate('./als-654-firebase-adminsdk-qxwee-87672135cc.json')
fb.initialize_app(cred, {
    'storageBucket': 'als-654.appspot.com',
    'databaseURL': 'https://als-654-default-rtdb.firebaseio.com/'
    })
bucket = storage.bucket()
ref = db.reference("/users/password")
image_ref = db.reference("/users/image")

# Flask Constructor
app = Flask(__name__)


# Main Page
@app.route("/")
def showHomePage():
    return "This is home page"


# The Password Interface
@app.route("/pm", methods=['POST'])
def pm():
    print("PM Call made")
    password = request.form.get("pswd")
    print("password: ", password)
    result = {"result": "ko"}
    otp = ref.get()

    if password == str(otp):
        result = {"result": "ok"}

    return json.dumps(result)


# Logic
@app.route("/debug", methods=["GET"])
def debug():
    imgs = cv.VideoCapture(0)
    result, image = imgs.read()

    if result:
        print("Photo Clicked")
        current_time = time_ns()
        file_name = 'gg' + str(current_time) + '.png'
        cv.imwrite(file_name, image)

        print("Photo Saved")

        blob = bucket.blob(file_name)
        blob.upload_from_filename(file_name)

        print("Photo Uploaded")
        blob.make_public()
        public_url = blob.public_url
        image_ref.set(public_url)
        print(public_url)
        return public_url
    else:
        return "https://www.elegantthemes.com/blog/wp-content/uploads/2020/08/000-http-error-codes.png"


if __name__ == "__main__":
    app.run(host="0.0.0.0")
