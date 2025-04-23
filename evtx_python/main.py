from flask import Flask, request,jsonify
import evtxParser_alpha
import os

app=Flask(__name__)

@app.route("/")
def index():
    file_path = request.args.get("filename")

    # Flask 기준으로 static 폴더 경로로 절대 경로 설정
    # main.py 기준으로 X-viewer/src/main/resources/static/evtx 까지 접근
    full_path = os.path.abspath(os.path.join(
        'X-viewer','src', 'main', 'resources', 'static', 'evtx', file_path
    ))

    print("🔥 전체 경로:", full_path)
    print("📦 파일 존재함?", os.path.exists(full_path))

    try:
        parsed_data = evtxParser_alpha.parse_evtx(full_path)
        return jsonify(parsed_data)
    except Exception as e:
        return jsonify({"error": str(e)}), 500

app.run()