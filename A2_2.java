package Ficheros;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.w3c.dom.*;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.IOException;
import java.nio.file.*;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;


/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 * @author DAM2_Alu05
 */
public class A2_2 {
    public static void main(String[] args) {
        //definimos variables
        Scanner input = new Scanner(System.in);
        int number = 1, list;
        long a;
        String directory;
        File file = new File(".\\AccesoADato\\src\\A2_2.java");
        //mientras que el numero introducido por teclado no sea 0 se ejecutara el programa infinitamente
        while (number != 0) {

            try {

                System.out.println("");
                System.out.println("Elija el numero asociado a la accion que desea realizar:");
                System.out.println("Listar archivos (1)");
                System.out.println("Crear carpeta (2)");
                System.out.println("Copiar archivo (3)");
                System.out.println("Mover archivo (4)");
                System.out.println("Leer archivo (5)");
                System.out.println("Escribir archivo (6)");
                System.out.println("Eliminar archivo (7)");
                System.out.println("Salir (0)");
                number = input.nextInt();

                switch (number) {

                    case 1 -> {
                        //en caso de ser 1 se pregunta si el usuario quiere el modo simple o detallado

                        System.out.println("Listar  en modo simple (1) o detallado? (2)");
                        list = input.nextInt();

                        switch (list) {
                            case 1 ->
                                //en el caso del modo simple solo se le muestra al usuario el nombre del fichero
                                    System.out.println(file.getName());
                            case 2 -> {
                                //en caso de el modo detallado obtenemos la ultima fecha de modificacion

                                a = file.lastModified();
                                //creamos un formato para mostrar esa fecha
                                DateFormat obj = new SimpleDateFormat("dd MMM yyyy HH:mm:ss");
                                //atribuimos ese formato a una nueva fecha con la informacion de la ultima modificacion
                                Date sol = new Date(a);
                                //mostramos al usuario los valores
                                System.out.println(file.getName() + " tamanho: " + file.getTotalSpace() / 100000000 + " bytes, ultima modificacion: " + obj.format(sol));

                            }
                            default -> System.out.println("Esa no era una opcion valida");
                        }

                    }

                    case 2 -> {

                        System.out.println("Como quieres llamar a tu carpeta");
                        directory = input.next();
                        //creamos la carpeta con una ruta relativa
                        File theDir = new File(".\\src\\" + directory);
                        //en caso de si la carpeta ya existe no se crea y se le avisa al usuario
                        if (!theDir.exists()) {
                            theDir.mkdirs();
                            System.out.println("directorio creado");
                        } else {
                            System.out.println("este directorio ya existe");
                        }

                    }

                    case 3 -> {
                        String extension;
                        //se le pregunta al usuario la ruta del fichero a copiar
                        System.out.println("como se llama/donde esta el fichero que quieres copiar");
                        Path source = Paths.get(".\\src\\" + input.next());
                        //se le pregunta al usuario la ruta de destino del fichero
                        System.out.println("a donde deseas copiarlo (NO AÑADIR LA EXTENSION)");
                        extension = FilenameUtils.getExtension(String.valueOf(source));
                        extension = "." + extension;
                        Path dst = Paths.get(".\\src\\" + input.next() + extension);
                        //se copia el fichero y se reemplaza en caso de ya existir
                        Files.copy(source, dst, StandardCopyOption.REPLACE_EXISTING);


                    }
                    case 4 -> {
                        String extension;
                        //se le pregunta al usuario la ruta del fichero a mover
                        System.out.println("como se llama/donde esta el fichero que quieres mover");
                        Path source = Paths.get(".\\src\\" + input.next());
                        while (!Files.exists(source)) {
                            System.out.println("ese archivo no existe, porfavor pruebe de nuevo");
                            source = Paths.get(".\\src\\" + input.next());
                        }
                        extension = FilenameUtils.getExtension(String.valueOf(source));
                        extension = "." + extension;

                        //se le pregunta al usuario la ruta de destino del fichero
                        System.out.println("a donde deseas moverlo (no añadir la extension)");
                        Path dst = Paths.get(".\\src\\" + input.next() + extension);
                        //se mueve el fichero y se reemplaza en caso de ya existir
                        Files.move(source, dst, StandardCopyOption.REPLACE_EXISTING);

                    }
                    case 5 -> {

                        //creamos una variable auxiliar
                        String aux;
                        //se le pregunta al usuario como se llama el archivo a leer
                        System.out.println("como se llama el archivo que quieres leer?, o presiona q para salir");
                        aux = input.next();

                        //se lee el archivo con la ruta que proporciono el usuario

                        System.out.println(FileUtils.readFileToString(new File(".\\src\\" + aux)));



                    }


                    case 6 -> {
                        String idPiso, direccionPiso, ciudadPiso, superficiePiso, numeroHabitacionesPiso, precioAlquilerPiso, valor;
                        String correcto;

                        // Cargar el archivo XML
                        File xmlFile = new File(".\\src\\BD\\pisos.xml");
                        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
                        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
                        Document doc = dBuilder.parse(xmlFile);
                        doc.getDocumentElement().normalize();
                        Element root = doc.getDocumentElement();
                        //
/*
                        NodeList nodeList = doc.getElementsByTagName("*");

                        for (int i = 1; i < nodeList.getLength(); i++) {
                            Node node = nodeList.item(i);
                            if (node.getNodeType() == Node.ELEMENT_NODE) {

                                Element element = (Element) node;
                                Node checkPisos = nodeList.item(0);
                                Element elementPisos = (Element) checkPisos;
                                Node checkPiso = nodeList.item(1);
                                Element elementPiso = (Element) checkPiso;
                                if (element.getNodeName()==elementPisos.getNodeName() || element.getNodeName()==elementPiso.getNodeName()){

                                }else {

                                    System.out.println("Elemento: " + element.getNodeName());
                                    System.out.println("escriba un valor para "+element.getNodeName());
                                    valor=input.next();
                                    // Crear el nuevo elemento <Piso>


                                    // Leer atributos del elemento
                                    // Verificar si el elemento tiene atributos
                                    NamedNodeMap attrMap = element.getAttributes();
                                    System.out.println(element.getAttribute(element.getNodeName()));

                                }


                            }
                        }
                        //


                         */


                        Element nuevoPiso = doc.createElement("Piso");


                        // Crear los subelementos del nuevo piso
                        Element id = doc.createElement("ID");
                        System.out.print("valor para el ID: ");
                        id.appendChild(doc.createTextNode(input.next())); // ID del nuevo piso

                        Element direccion = doc.createElement("Direccion");
                        System.out.print("valor para la direccion: ");
                        direccion.appendChild(doc.createTextNode(input.next()));

                        Element ciudad = doc.createElement("Ciudad");
                        System.out.print("valor para la ciudad: ");
                        ciudad.appendChild(doc.createTextNode(input.next()));

                        Element superficie = doc.createElement("Superficie");
                        System.out.print("valor para la superficie: ");
                        superficie.appendChild(doc.createTextNode(input.next()));

                        Element numHabitaciones = doc.createElement("NumeroHabitaciones");
                        System.out.print("valor para el numero de habitaciones: ");
                        numHabitaciones.appendChild(doc.createTextNode(input.next()));

                        Element precioAlquiler = doc.createElement("PrecioAlquiler");
                        System.out.print("valor para el precio del alquiler: ");
                        precioAlquiler.appendChild(doc.createTextNode(input.next()));

                        // Añadir los subelementos al elemento <Piso>
                        nuevoPiso.appendChild(id);
                        nuevoPiso.appendChild(direccion);
                        nuevoPiso.appendChild(ciudad);
                        nuevoPiso.appendChild(superficie);
                        nuevoPiso.appendChild(numHabitaciones);
                        nuevoPiso.appendChild(precioAlquiler);

                        // Añadir el nuevo piso al nodo raíz <Pisos>
                        Node pisos = doc.getElementsByTagName("Pisos").item(0);
                        pisos.appendChild(nuevoPiso);


                        // Guardar los cambios en el archivo XML
                        TransformerFactory transformerFactory = TransformerFactory.newInstance();
                        Transformer transformer = transformerFactory.newTransformer();
                        DOMSource source = new DOMSource(doc);
                        StreamResult result = new StreamResult(xmlFile);

                        // Mostrar los cambios en consola antes de guardar
                        StreamResult consoleResult = new StreamResult(System.out);
                        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
                        transformer.transform(source, consoleResult);

                        // Guardar los cambios en el archivo
                        transformer.transform(source, result);



                     /*   if(correcto=="Y"){
                            StreamResult resultado = new StreamResult(xmlFile);
                            transformer.transform(source, result);
                            System.out.println("Nuevo piso añadido al archivo XML.");
                        }else{
                            System.out.println("No se realizaron cambio en el archivo xml");
                        }
                            */


                    }
                    case 7 -> {
                        //se pregunta al usuario que directorio o archivo desea eliminar
                        System.out.println("que directorio o archivo desea eliminar");
                        directory = input.next();
                        //se crea una referencia a la ruta de ese directorio/archivo
                        File check = new File(".\\src\\" + directory);

                        //se confirma si es un directorio
                        if (check.isDirectory()) {
                            //se confirma si existe
                            if (check.exists()) {
                                //se borra el directorio y se avisa al usuario
                                FileUtils.deleteDirectory(new File(".\\src\\" + directory));
                                System.out.println("el directorio fue borrado");
                            } else {
                                //se avisa al usuario de que no se encontro el directorio
                                System.out.println("no se encontro el directorio");
                            }
                            //en caso de no ser un directorio se confirma si existe y se borra el fichero
                        } else {
                            if (check.exists()) {
                                Path fileToDeletePath = Paths.get(".\\src\\" + directory);
                                Files.delete(fileToDeletePath);
                                //se le avisa al usuario
                                System.out.println("el archivo fue borrado");
                            } else {
                                //se avisa al usuario de que no se encontro el fichero
                                System.out.println("no se encontro el archivo");
                            }
                        }


                    }

                    case 0 -> {
                        System.out.println("xao");
                    }
                    default ->
                        //el usuario elijio una opcion no valida dentro de los valores del int
                            System.out.println("Elije una opcion valida porfavor");
                }
            } catch (InputMismatchException e) {
                //esta excepcion ocurre cuando el usuario elije una opcion la cual no es un int
                System.err.println("solo numeros, o valores entre el 0 y el 8 porfavor");
                //se DEBE de limpiar el valor del input o se quedara con el valor no valido entrando en un bucle infinito de la excepcion InputMismatchException
                input.nextLine();
            } catch (IOException e) {
                //esta excepcion ocurre cuando el usuario elije un archivo no existente
                System.err.println("archivo no encontrado");
            } catch (ParserConfigurationException e) {
                throw new RuntimeException(e);
            } catch (SAXException e) {
                throw new RuntimeException(e);
            } catch (TransformerConfigurationException e) {
                throw new RuntimeException(e);
            } catch (TransformerException e) {
                throw new RuntimeException(e);
            }
        }

        input.close();


    }

}


