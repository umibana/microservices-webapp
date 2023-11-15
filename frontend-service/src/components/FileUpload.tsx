import { useState } from "react";
import axios from "axios";
import { Button } from "./ui/button";

function FileUpload() {
  const [selectedFile, setSelectedFile] = useState(null);
  const [uploadStatus, setUploadStatus] = useState("");

  const handleFileChange = (event: any) => {
    setSelectedFile(event.target.files[0]);
  };

  const handleFileUpload = async () => {
    if (selectedFile) {
      const formData = new FormData();
      formData.append("file", selectedFile);

      try {
        setUploadStatus("Subiendo...");
        // Replace 'your-upload-endpoint' with your server's upload endpoint
        await axios.post("http://127.0.0.1:8080/score/upload", formData, {
          headers: {
            "Content-Type": "multipart/form-data",
          },
        });
        setUploadStatus("Archivo se subio correctamente");
        // Handle the response as needed
      } catch (error) {
        setUploadStatus("Error al subir");
        console.error("Error uploading the file:", error);
      }
    }
  };

  return (
    <div>
      <input type="file" onChange={handleFileChange} />
      <Button onClick={handleFileUpload}>Upload</Button>
      {uploadStatus && <p>{uploadStatus}</p>}
    </div>
  );
}

export default FileUpload;
