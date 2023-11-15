import { Button } from "@/components/ui/button";
import { Input } from "@/components/ui/input";
import {
  Table,
  TableBody,
  TableCell,
  TableHead,
  TableHeader,
  TableRow,
} from "@/components/ui/table";
import { useState } from "react";
import axios from "axios";

interface userInterface {
  id: string;
  rut: string;
  name: string;
  surname: string;
  birthdate: string;
  schoolType: string;
  graduationYear: number;
  discount: number;
  enrollStatus: boolean;
  usingCredit: boolean;
}

export function SearchButton() {
  const [inputValue, setInputValue] = useState("");
  const [userInfo, setUserInfo] = useState<userInterface>();
  const [userSummary, setUserSummary] = useState("");
  const [installments, setInstallments] = useState<any[]>([]);

  const handleInputChange = (event: any) => {
    setInputValue(event.target.value);
  };

  const handleSearch = async (rut: string) => {
    setUserSummary("");
    const response = await axios(`http://127.0.0.1:8080/user/${rut}`);
    setUserInfo(response.data);
    const installmentResponse = await axios(
      `http://127.0.0.1:8080/installment/${rut}`,
    );
    setInstallments(installmentResponse.data);
    // We clean previous info
  };

  const handleSummary = async (rut: string) => {
    const summaryResponse = await axios(
      `http://127.0.0.1:8080/user/summary/${rut}`,
    );
    setUserSummary(summaryResponse.data);
    setUserInfo(undefined);
  };

  function toggleIsPaid(id: number) {
    axios.post(`http://127.0.0.1:8080/installment/updatePaid/${id}`);
    if (userInfo !== undefined) {
      handleSearch(userInfo.rut);
    }
  }

  return (
    <div>
      <h1> Busqueda de usuario </h1>
      <div className="flex my-4 gap-4">
        <Input
          type="rut"
          placeholder="Ingrese Rut del usuario"
          onChange={handleInputChange}
        />
        <Button type="submit" onClick={() => handleSearch(inputValue)}>
          Buscar Cuotas
        </Button>
        <Button type="submit" onClick={() => handleSummary(inputValue)}>
          Buscar Resumen
        </Button>
      </div>

      {userInfo && (
        <div className="flex flex-col">
          <h1>
            Nombre: {userInfo.name} {userInfo.surname}{" "}
          </h1>
          <p>RUT: {userInfo.rut} </p>
          <p>Fecha de nacimiento: {userInfo.birthdate} </p>
          <p>Tipo de colegio: {userInfo.schoolType} </p>
          <p>Año de graduacion: {userInfo.graduationYear} </p>
          <p>Estado Matricula: {userInfo.enrollStatus ? "Si" : "No"} </p>
          <p>Usa credito: {userInfo.usingCredit ? "Si" : "No"} </p>
          {installments && (
            <Table>
              <TableHeader>
                <TableRow></TableRow>
                <TableHead> ID </TableHead>
                <TableHead> Fecha </TableHead>
                <TableHead> Monto </TableHead>
                <TableHead> Pagado? </TableHead>
                <TableHead> Fecha de pago </TableHead>
              </TableHeader>
              <TableBody>
                {installments.map((installment) => (
                  <TableRow key={installment.id}>
                    <TableCell> {installment.id} </TableCell>
                    <TableCell> {installment.date} </TableCell>
                    <TableCell> {installment.amount} </TableCell>
                    <TableCell> {installment.paid ? "Si" : "No"} </TableCell>
                    <TableCell>
                      {installment.paidDate ? installment.paidDate : "-"}
                    </TableCell>
                    <TableCell>
                      {" "}
                      <Button onClick={() => toggleIsPaid(installment.id)}>
                        {" "}
                        Pagar{" "}
                      </Button>{" "}
                    </TableCell>
                  </TableRow>
                ))}
              </TableBody>
            </Table>
          )}
        </div>
      )}
      {userSummary && <p className="whitespace-pre-line"> {userSummary} </p>}
    </div>
  );
}
