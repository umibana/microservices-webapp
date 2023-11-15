import axios from "axios";
import { useState } from "react";
import {
  Select,
  SelectContent,
  SelectItem,
  SelectTrigger,
  SelectValue,
} from "@/components/ui/select";
import { Checkbox } from "@/components/ui/checkbox";
import { Label } from "@/components/ui/label";
import { Button } from "@/components/ui/button";
import { Input } from "@/components/ui/input";

export default function UserCreationForm() {
  const [formData, setFormData] = useState({
    name: "",
    surname: "",
    rut: "",
    birthdate: "",
    schoolType: "MUNICIPAL",
    graduationYear: "",
    enrollStatus: false,
    usingCredit: false,
  });

  const handleChange = (e: any) => {
    console.log(e.type);
    const { name, value, type, checked } = e.target;
    setFormData({
      ...formData,
      [name]: type === "checkbox" ? checked : value,
    });
  };

  const handleSelect = (value: any) => {
    setFormData({
      ...formData,
      schoolType: value,
    });
  };
  const handleSubmit = async (e: any) => {
    console.log(formData);
    e.preventDefault();
    axios.post("http://127.0.0.1:8080/user", formData);
    // Here you can handle the form submission (e.g., sending data to a server)
  };

  return (
    <div>
      <h1> Creación de usuario </h1>
      <form
        onSubmit={handleSubmit}
        className="space-y-4 flex flex-col items-center my-4"
      >
        <Input
          type="text"
          id="name"
          name="name"
          placeholder="Nombre"
          onChange={handleChange}
        />
        <Input
          type="text"
          id="surname"
          name="surname"
          placeholder="Apellidos"
          onChange={handleChange}
        />
        <Input
          type="text"
          id="rut"
          name="rut"
          placeholder="RUT:1.234.456-0"
          onChange={handleChange}
        />
        <Input
          type="date"
          id="birthdate"
          name="birthdate"
          onChange={handleChange}
        />
        <Select onValueChange={handleSelect}>
          <SelectTrigger className="w-[280px]">
            <SelectValue placeholder="Tipo de colegio" />
          </SelectTrigger>
          <SelectContent>
            <SelectItem value="MUNICIPAL"> Municipal </SelectItem>
            <SelectItem value="SUBVENCIONADO"> Subvencionado </SelectItem>
            <SelectItem value="PRIVADO"> Privado </SelectItem>
          </SelectContent>
        </Select>
        <Input
          type="number"
          id="graduationYear"
          name="graduationYear"
          placeholder="Año de graduación"
          onChange={handleChange}
        />
        <div className="flex items-center space-x-2" onChange={handleChange}>
          <Checkbox id="enrollStatus" name="enrollStatus" />
          <Label htmlFor="enrollStatus">Matricula pagada?</Label>
        </div>
        <div className="flex items-center space-x-2" onChange={handleChange}>
          <Checkbox id="usingCredit" name="usingCredit" />
          <Label htmlFor="usingCredit">Paga con credito</Label>
        </div>
        <Button type="submit">Crear usuario</Button>
      </form>
    </div>
  );
}
